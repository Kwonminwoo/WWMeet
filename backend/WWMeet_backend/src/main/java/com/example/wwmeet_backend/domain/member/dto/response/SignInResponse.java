package com.example.wwmeet_backend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
}
