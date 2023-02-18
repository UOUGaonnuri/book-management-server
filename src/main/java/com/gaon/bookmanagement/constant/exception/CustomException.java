package com.gaon.bookmanagement.constant.exception;

import com.gaon.bookmanagement.constant.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    ErrorCode errorCode;
}
