package com.example.wwmeet_backend.domain.member.service;

import com.example.wwmeet_backend.domain.member.entity.SecurityMember;
import com.example.wwmeet_backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityMember(memberRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("로그인 실패")));
    }
}
