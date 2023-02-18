package com.gaon.bookmanagement.security.auth;

import com.gaon.bookmanagement.constant.enums.ErrorCode;
import com.gaon.bookmanagement.constant.exception.CustomException;
import com.gaon.bookmanagement.domain.Member;
import com.gaon.bookmanagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NAME_NOT_FIND);
        });
        return new PrincipalDetails(member);
    }
}
