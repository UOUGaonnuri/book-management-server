package com.gaon.bookmanagement.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATION_MEMBER(HttpStatus.CONFLICT, "해당 아이디가 존재합니다."),
    DUPLICATION_EMAIL(HttpStatus.CONFLICT, "해당 이메일이 존재합니다."),
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "입력하신 정보가 일치하지 않습니다. 다시 입력해주세요."),
    USER_NAME_NOT_FIND(HttpStatus.NOT_FOUND, "해당 아이디가 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    DUPLICATION_ISBN(HttpStatus.CONFLICT, "해당 ISBN은 존재합니다."),
    NOT_FIND_BOOK(HttpStatus.NOT_FOUND, "해당 ID의 책은 존재하지 않습니다."),
    NOT_FIND_USER(HttpStatus.NOT_FOUND, "해당 ID의 유저는 존재하지 않습니다."),
    FILE_NAME_NULL(HttpStatus.BAD_REQUEST, "파일의 OriginalName이 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "파일 업로드에 실피했습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지않은 RefreshToken 입니다."),
    CONTEXT_NOT_FIND(HttpStatus.NOT_FOUND, "Security Context에 해당 인증 정보가 없습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "해당 RefreshToken이 만료되었습니다. 재 로그인 해주세요."),
    CAN_NOT_SEARCH(HttpStatus.NOT_FOUND, "검색하신 책은 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
