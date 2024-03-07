package com.example.wwmeet_backend.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInRequest {
    private String email;
    private String password;

    @Builder
    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
