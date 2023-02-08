package com.gaon.bookmanagement.constant.exception;

import com.gaon.bookmanagement.constant.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomTokenException extends RuntimeException{
    private ErrorCode errorCode;
}
