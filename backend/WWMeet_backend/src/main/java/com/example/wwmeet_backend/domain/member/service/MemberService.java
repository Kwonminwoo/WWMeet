package com.example.wwmeet_backend.domain.member.service;

import com.example.wwmeet_backend.domain.member.domain.Member;
import com.example.wwmeet_backend.domain.member.dto.SignInRequest;
import com.example.wwmeet_backend.domain.member.dto.SignUpRequest;
import com.example.wwmeet_backend.domain.member.jwt.JwtProvider;
import com.example.wwmeet_backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String signIn(SignInRequest signInRequest) {
        Member targetMember = memberRepository.findByEmail(signInRequest.getEmail())
            .orElseThrow(() -> new RuntimeException());

        if (passwordEncoder.matches(signInRequest.getPassword(), targetMember.getPassword())) {
            return jwtProvider.createToken(targetMember);
        }
        throw new RuntimeException("사용자 정보가 일치하지 않습니다.");
    }

    public void signUp(SignUpRequest signUpRequest) {
        Member member = Member.builder()
            .email(signUpRequest.getEmail())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .build();

        memberRepository.save(member);
    }
}
