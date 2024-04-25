package com.example.wwmeet_backend.domain.participant.exception;

import com.example.wwmeet_backend.global.exception.BaseException;
import com.example.wwmeet_backend.global.exception.ErrorCode;

public class DuplicateAppointmentException extends BaseException {

    public DuplicateAppointmentException(
        ErrorCode errorCode) {
        super(errorCode);
    }
}
