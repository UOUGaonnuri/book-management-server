package com.gaon.bookmanagement.security.auth;

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
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");
        });
        return new PrincipalDetails(member);
    }
}
