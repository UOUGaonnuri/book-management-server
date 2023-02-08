package com.gaon.bookmanagement.constant.handler;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.constant.exception.CustomLoginException;
import com.gaon.bookmanagement.constant.exception.CustomMemberException;
import com.gaon.bookmanagement.constant.exception.CustomTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(BindingResult bindingResult) {
        log.info("Enter handleValidationException()");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createFail(bindingResult));
    }

    @ExceptionHandler(CustomMemberException.class)
    public ResponseEntity<ApiResponse<Boolean>> userDuplicatedException(CustomMemberException e) {
        log.info("get Error : " + e.getErrorCode().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(Boolean.TRUE, e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(CustomLoginException.class)
    public ResponseEntity<ApiResponse<?>> userLoginException(CustomLoginException e) {
        log.info("get Error : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(Boolean.FALSE, e.getMessage()));
    }

    @ExceptionHandler(CustomTokenException.class)
    public ResponseEntity<ApiResponse<?>> invalidTokenException(CustomTokenException e) {
        log.info("get Error : " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(Boolean.FALSE, e.getMessage()));
    }
}
