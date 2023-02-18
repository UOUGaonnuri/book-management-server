package com.gaon.bookmanagement.constant.handler;

import com.gaon.bookmanagement.constant.dto.ApiResponse;
import com.gaon.bookmanagement.constant.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(BindingResult bindingResult) {
        log.info("Enter handleValidationException()");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createFail(bindingResult));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        log.error(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.createError(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage()));
    }
}
