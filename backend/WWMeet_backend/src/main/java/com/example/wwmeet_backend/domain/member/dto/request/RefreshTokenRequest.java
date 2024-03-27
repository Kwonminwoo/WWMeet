package com.example.wwmeet_backend.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}
