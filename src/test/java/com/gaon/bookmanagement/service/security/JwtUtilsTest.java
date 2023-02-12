package com.gaon.bookmanagement.service.security;

import com.gaon.bookmanagement.domain.Member;
import com.gaon.bookmanagement.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtUtilsTest {

    @Value("${jwt.security.key}")
    private String secret;


    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;

    private String username = "songchoon";

    @Test
    void init() {
        Member member = Member.builder().username(username).password("qkrtjdcns1!").email("choon@naver.com").build();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
    }

    @Test
    void createAccessToken_발급() {
        String accessToken = jwtUtils.createAccessToken(username);
        String findUsername = jwtUtils.getUsername(accessToken);

        assertEquals(findUsername, username);
    }

    @Test
    void createRefreshToken_발급() {

    }

    @Test
    void getAuthentication() {

    }

    @Test
    void getUsername() {
        String accessToken = jwtUtils.createAccessToken(username);
        String findUsername = jwtUtils.getUsername(accessToken);

        assertEquals(username, findUsername);
    }

    @Test
    void getExpiration() {
        String accessToken = jwtUtils.createAccessToken(username);
        Long expiration = new Date().getTime() + JwtUtils.ACCESS_TOKEN_VALID_TIME;
        Long now = new Date().getTime();
        Long expected = expiration - now;
        Long getExpiration = jwtUtils.getExpiration(accessToken);

        assertEquals(now, getExpiration);
    }

    @Test
    void resolveToken() {
    }

    @Test
    void validateToken() {
    }
}