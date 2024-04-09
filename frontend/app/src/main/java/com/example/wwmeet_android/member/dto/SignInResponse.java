package com.example.wwmeet_android.member.dto;

public class SignInResponse {
    private String accessToken;
    private String refreshToken;

    public SignInResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
