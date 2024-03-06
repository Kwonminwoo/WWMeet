package com.example.wwmeet_backend.member.controller;

import com.example.wwmeet_backend.member.dto.SignInRequest;
import com.example.wwmeet_backend.member.dto.SignUpRequest;
import com.example.wwmeet_backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
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
    public Long signIn(@RequestBody SignInRequest signInRequest) {
        return memberService.signIn(signInRequest);
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
    }

}
