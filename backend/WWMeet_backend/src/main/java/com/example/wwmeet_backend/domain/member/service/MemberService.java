package com.example.wwmeet_backend.domain.member.service;

import com.example.wwmeet_backend.domain.member.dto.request.RefreshTokenRequest;
import com.example.wwmeet_backend.domain.member.dto.response.RefreshTokenResponse;
import com.example.wwmeet_backend.domain.member.dto.response.SignInResponse;
import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.member.dto.request.SignInRequest;
import com.example.wwmeet_backend.domain.member.dto.request.SignUpRequest;
import com.example.wwmeet_backend.domain.member.jwt.JwtProvider;
import com.example.wwmeet_backend.domain.member.repository.MemberRepository;
import com.example.wwmeet_backend.domain.member.repository.RefreshTokenRepository;
import com.example.wwmeet_backend.global.exception.DataNotFoundException;
import com.example.wwmeet_backend.global.util.CurrentMemberService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final CurrentMemberService currentMemberService;

    public SignInResponse signIn(SignInRequest signInRequest) {
        Member targetMember = memberRepository.findByEmail(signInRequest.getEmail())
            .orElseThrow(DataNotFoundException::new);

        if (passwordEncoder.matches(signInRequest.getPassword(), targetMember.getPassword())) {
            return new SignInResponse(jwtProvider.createAccessToken(targetMember),
                jwtProvider.createRefreshToken());
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

    public RefreshTokenResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken())
            .orElseThrow(() -> new RuntimeException());// TODO jwt 만료 커스텀 예외 추가

        return new RefreshTokenResponse(
            jwtProvider.createAccessToken(currentMemberService.getCurrentMember()));
    }
}
