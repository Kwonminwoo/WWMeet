package com.example.wwmeet_backend.domain.member.controller;

import com.example.wwmeet_backend.domain.member.dto.request.RefreshTokenRequest;
import com.example.wwmeet_backend.domain.member.dto.request.SignInRequest;
import com.example.wwmeet_backend.domain.member.dto.request.SignUpRequest;
import com.example.wwmeet_backend.domain.member.dto.response.SignInResponse;
import com.example.wwmeet_backend.domain.member.service.MemberService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
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
    public ResponseEntity<ResponseAPI> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(
            ResponseAPI.response("로그인 성공", memberService.signIn(signInRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseAPI> signUp(@RequestBody SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseAPI.response("회원가입 성공"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseAPI> refresh(
        @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(ResponseAPI.response("토큰 재발급 성공",
            memberService.refreshAccessToken(refreshTokenRequest)));
    }

}
