package com.gaon.bookmanagement.service.member;

import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomException;
import com.gaon.bookmanagement.domain.Member;
import com.gaon.bookmanagement.dto.request.JoinRequestDto;
import com.gaon.bookmanagement.dto.request.LoginRequestDto;
import com.gaon.bookmanagement.dto.response.JoinResponseDto;
import com.gaon.bookmanagement.dto.response.LoginResponseDto;
import com.gaon.bookmanagement.dto.response.LogoutDto;
import com.gaon.bookmanagement.dto.response.ReissueDto;
import com.gaon.bookmanagement.repository.MemberRepository;
import com.gaon.bookmanagement.service.security.JwtUtils;
import com.gaon.bookmanagement.service.security.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    private final RedisUtils redisUtils;
    private final RedisTemplate redisTemplate;

    public JoinResponseDto join(JoinRequestDto joinRequestDto) {
        usernameDuplicateCheck(joinRequestDto.getUsername());
        emailDuplicateCheck(joinRequestDto.getEmail());
        Member member = joinRequestDto.toEntity(passwordEncoder);

        Member joinMember = memberRepository.save(member);

        return new JoinResponseDto(joinMember);
    }

    public void usernameDuplicateCheck(String username) {
        boolean result = memberRepository.existsByUsername(username);

        if(result) {
            // 중복되는 경우
            throw new CustomException(ErrorCode.DUPLICATION_MEMBER);
        }
    }

    public void emailDuplicateCheck(String email) {
        boolean result = memberRepository.existsByEmail(email);
        if(result) {
            // 중복되는 경우
            throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
        }
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws RuntimeException{
//        https://wildeveloperetrain.tistory.com/56
        Member member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NAME_NOT_FIND);
        });

        // 비밀번호가 틀렸을때 오류 예외 발생시키기
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_ERROR);
        }

        String accessToken = jwtUtils.createAccessToken(member.getUsername());
        String refreshToken = redisUtils.getData("RT"+member.getUsername());

        if(refreshToken == null ){
            refreshToken = jwtUtils.createRefreshToken(loginRequestDto.getUsername());
            redisUtils.setDataExpire("RT:"+member.getUsername(), refreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME);
        }

        return LoginResponseDto.builder()
                .username(member.getUsername())
                .accessToken(accessToken)
                .build();
    }

    public ReissueDto reissue(String refreshToken) {
        if(!jwtUtils.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String username = jwtUtils.getUsername(refreshToken);
        String savedRefreshToken = redisUtils.getData("RT:"+username);
        if(refreshToken.isEmpty() || !refreshToken.equals(savedRefreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        } else {
            String newAccessToken = jwtUtils.createAccessToken(username);

            return new ReissueDto(newAccessToken);
        }
    }

    public LogoutDto logout(String accessToken) {
        if(!jwtUtils.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        Authentication authentication = jwtUtils.getAuthentication(accessToken);
        String username = authentication.getName();

        if(redisTemplate.opsForValue().get("RT:"+username) != null) {
            log.info("getRefreshToken : " + redisTemplate.opsForValue().get("RT:"+username));
            redisTemplate.delete("RT:"+username);
        }

        Long expiration = jwtUtils.getExpiration(accessToken);
        log.info("expiration : " + expiration);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration);

        return new LogoutDto(username);
    }
}
