package com.gaon.bookmanagement.dto.request;

import com.gaon.bookmanagement.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "[a-z1-9]{4,12}", message = "아이디는 4~12자 특수문자를 제외한 영문 소문자, 숫자를 사용하세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}", message = "비밀번호는 4~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @Builder
    public Member toEntity() {
        log.info("enter the toEntity()");
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
