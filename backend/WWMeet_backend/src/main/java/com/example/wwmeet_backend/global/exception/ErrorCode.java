package com.example.wwmeet_backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."),
    LOGIN_INFO_NOT_MATCH(HttpStatus.FORBIDDEN, "로그인 정보가 일치하지 않습니다."),
    LOGIN_INFO_EXPIRED(HttpStatus.FORBIDDEN, "로그인 정보가 만료되었습니다."),
    DUPLICATE_APPOINTMENT(HttpStatus.CONFLICT, "이미 참가한 약속입니다."),
    ;
    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
