package com.example.wwmeet_backend.domain.member.controller;

import com.example.wwmeet_backend.domain.member.dto.request.RefreshTokenRequest;
import com.example.wwmeet_backend.domain.member.dto.request.SignInRequest;
import com.example.wwmeet_backend.domain.member.dto.request.SignUpRequest;
import com.example.wwmeet_backend.domain.member.dto.response.RefreshTokenResponse;
import com.example.wwmeet_backend.domain.member.dto.response.SignInResponse;
import com.example.wwmeet_backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(memberService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(
        @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(memberService.refreshAccessToken(refreshTokenRequest));
    }

}
