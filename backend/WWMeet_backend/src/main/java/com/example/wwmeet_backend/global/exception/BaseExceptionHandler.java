package com.example.wwmeet_backend.global.exception;

import com.example.wwmeet_backend.global.response.ResponseAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseAPI> exceptionHandler(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ResponseAPI.response(
            errorCode.getMessage()));
    }
}
