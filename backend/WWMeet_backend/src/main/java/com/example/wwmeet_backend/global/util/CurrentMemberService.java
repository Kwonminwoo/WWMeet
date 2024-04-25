package com.example.wwmeet_backend.global.util;

import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.member.repository.MemberRepository;
import com.example.wwmeet_backend.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentMemberService {

    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberEmail = authentication.getName();
        return memberRepository.findByEmail(currentMemberEmail)
            .orElseThrow(DataNotFoundException::new);
    }

}
