package com.example.wwmeet_backend.domain.member.exception;

import com.example.wwmeet_backend.global.exception.BaseException;
import com.example.wwmeet_backend.global.exception.ErrorCode;

public class LoginValidateException extends BaseException {

    public LoginValidateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
