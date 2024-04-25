package com.example.wwmeet_backend.global.exception;

public class DataNotFoundException extends BaseException{

    public DataNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND);
    }
}
