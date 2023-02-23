package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomException;
import com.gaon.bookmanagement.dto.request.JoinRequestDto;
import com.gaon.bookmanagement.dto.request.LoginRequestDto;
import com.gaon.bookmanagement.dto.response.JoinResponseDto;
import com.gaon.bookmanagement.dto.response.LoginResponseDto;
import com.gaon.bookmanagement.dto.response.LogoutDto;
import com.gaon.bookmanagement.dto.response.ReissueDto;
import com.gaon.bookmanagement.service.member.AuthService;
import com.gaon.bookmanagement.service.security.CookieUtils;
import com.gaon.bookmanagement.service.security.JwtUtils;
import com.gaon.bookmanagement.service.security.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthApiController {
    private final AuthService memberService;
    private final RedisUtils redisUtils;
    private final JwtUtils jwtUtils;

    @PostMapping("/api/join")
    public ResponseEntity<ApiResponse<String>> join(@RequestBody @Valid JoinRequestDto joinRequestDto) {
        JoinResponseDto joinMember = memberService.join(joinRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("Join Success!"));
    }

    @GetMapping("/api/check/{username}/username")
    public ResponseEntity<ApiResponse<String>> usernameDuplicateCheck(@PathVariable String username) {
        memberService.usernameDuplicateCheck(username);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("사용가능한 아이디입니다."));
    }

    @GetMapping("/api/check/{email}/email")
    public ResponseEntity<ApiResponse<String>> emailDuplicateCheck(@PathVariable String email) {
        memberService.emailDuplicateCheck(email);
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent("사용가능한 이메일입니다."));
    }

    @PostMapping("/api/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws RuntimeException {
        LoginResponseDto loginResponseDto = memberService.login(loginRequestDto);
        String refreshToken = redisUtils.getData("RT:"+loginRequestDto.getUsername());
        ResponseCookie refreshTokenCookie = CookieUtils.createCookie(JwtUtils.REFRESH_TOKEN_NAME, refreshToken);
        log.info("end memberService.login()");

        return ResponseEntity.ok().header(SET_COOKIE, refreshTokenCookie.toString()).body(ApiResponse.createSuccess(loginResponseDto, "Login Success!"));
    }

    @PostMapping("/api/member/reissue")
    public ResponseEntity<ApiResponse<ReissueDto>> reissue(@CookieValue(value = JwtUtils.REFRESH_TOKEN_NAME, defaultValue = "") String refreshToken) {
        if (!refreshToken.isEmpty()) {
            ReissueDto result = memberService.reissue(refreshToken);
            return ResponseEntity.ok().body(ApiResponse.createSuccess(result, "Reissue Success!"));
        } else {
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    @PostMapping("/api/member/logout")
    public ResponseEntity<ApiResponse<LogoutDto>> logout(@CookieValue(value = JwtUtils.REFRESH_TOKEN_NAME, defaultValue = "") String refreshToken,
                                                         HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtils.resolveToken(request);
        LogoutDto result = memberService.logout(accessToken);

        if(!refreshToken.isEmpty()) {
            CookieUtils.deleteCookie(request, response, JwtUtils.REFRESH_TOKEN_NAME);
        }

        return ResponseEntity.ok().body(ApiResponse.createSuccess(result, "Logout Success!"));
    }
}
