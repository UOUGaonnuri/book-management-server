package com.gaon.bookmanagement.controller;

import com.gaon.bookmanagement.domain.Member;
import com.gaon.bookmanagement.dto.request.JoinRequestDto;
import com.gaon.bookmanagement.repository.MemberRepository;
import com.gaon.bookmanagement.service.member.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthApiControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthService authService;
    @Autowired
    EntityManager em;


    public void clear() {
        em.flush();
        em.clear();
    }

//    @Test
//    void 회원가입_성공() {
//        JoinRequestDto joinMember= new JoinRequestDto("choon", "qkrtjdcns1!", "songchoon@naver.com");
//        Member member = joinMember.toEntity();
//        Member saveMember = memberRepository.save(member);
//
//        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
//
//        assertEquals(saveMember.getId(), findMember.get().getId());
//        assertEquals(saveMember, findMember.get());
//    }

    @Test
    void 회원가입_실패() throws Exception {
        MockHttpServletRequestBuilder builder = post("/join")
                .content("{\"username\": \"cho\", \"password\": \"qkrtjdcns1!\", \"email\": \"songchoon10@naver.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String message = result.getResolvedException().getMessage();
        System.out.println("message : " + message);

        assertTrue(message.contains("아이디는 4~12자 특수문자를 제외한 영문 소문자, 숫자를 사용하세요."));
    }

    @Test
    void 아이디중복체크_실패() throws Exception {
        MockHttpServletRequestBuilder builder = get("/check/songchoon/username");

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("해당 아이디가 존재합니다."));
    }

    @Test
    void 아이디중복체크_성공() throws Exception {
        MockHttpServletRequestBuilder builder = get("/check/gaonstudy/username");

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("사용가능한 아이디입니다."));
    }

    @Test
    void 로그인_성공() throws Exception {
        MockHttpServletRequestBuilder builder = post("/login")
                .content("{\"username\": \"songchoon\", \"password\": \"qkrtjdcns1!\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("Bearer "));
        assertTrue(contentAsString.contains("Login Success!"));
    }

    @Test
    void 로그인_실패_아이디오류() throws Exception {
        MockHttpServletRequestBuilder builder = post("/login")
                .content("{\"username\": \"oon\", \"password\": \"qkrtjdcns1!\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("해당 아이디가 존재하지 않습니다."));
    }

    @Test
    void 로그인_실패_비밀번호오류() throws Exception {
        MockHttpServletRequestBuilder builder = post("/login")
                .content("{\"username\": \"songchoon\", \"password\": \"qkrtjdc\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("입력하신 정보가 일치하지 않습니다. 다시 입력해주세요."));
    }

    @Test
    void reissue() {

    }

    @Test
    void logout() throws Exception {

        Cookie cookie = new Cookie("Set-Cookie", "refresh_token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb25nY2hvb24iLCJleHAiOjE2NzY0NTY2Nzh9.5kllr4WCI9JBZslfiDJ-vFTKIkvX3r479Cj9lzYbAtU; Path=/; Max-Age=60480000; Expires=Wed, 08 Jan 2025 10:24:38 GMT; Secure; HttpOnly; SameSite=None");

        MockHttpServletRequestBuilder builder = post("/logout")
                .cookie(cookie)
                .content("{\"accessToken\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb25nY2hvb24iLCJpYXQiOjE2NzU4NTE4NzgsImV4cCI6MTY3NTg1MzY3OH0.WUK4HPA7ffwSkkl3WVFzKF8LSgkKF9LHl3HMbJodblE\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("content : " + contentAsString);

        assertTrue(contentAsString.contains("Logout Success!"));
    }
}