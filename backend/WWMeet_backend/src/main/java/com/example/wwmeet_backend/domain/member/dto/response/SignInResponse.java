package com.example.wwmeet_backend.domain.member.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
}
