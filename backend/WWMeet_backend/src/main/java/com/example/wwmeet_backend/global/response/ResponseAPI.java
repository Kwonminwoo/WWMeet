package com.example.wwmeet_backend.global.response;

public class ResponseAPI {
    private String message;
    private int code;
    private Object data;

    public ResponseAPI(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ResponseAPI(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseAPI(String message) {
        this.message = message;
    }

    public static ResponseAPI response(String message, int code, Object data) {
        return new ResponseAPI(message, code, data);
    }

    public static ResponseAPI response(String message, Object data) {
        return new ResponseAPI(message, data);
    }

    public static ResponseAPI response(String message) {
        return new ResponseAPI(message);
    }
}
