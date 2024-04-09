package com.example.wwmeet_android.network;

public class ResponseAPI<T> {
    private String message;
    private int code;
    private T data;

    public ResponseAPI(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ResponseAPI(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseAPI(String message) {
        this.message = message;
    }

    public ResponseAPI() {
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
