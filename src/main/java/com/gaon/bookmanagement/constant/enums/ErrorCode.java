package com.gaon.bookmanagement.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATION_MEMBER(HttpStatus.CONFLICT, "해당 아이디가 존재합니다."),
    DUPLICATION_EMAIL(HttpStatus.CONFLICT, "해당 이메일이 존재합니다."),
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "입력하신 정보가 일치하지 않습니다. 다시 입력해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 아이디가 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    DUPLICATION_ISBN(HttpStatus.CONFLICT, "해당 ISBN은 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
