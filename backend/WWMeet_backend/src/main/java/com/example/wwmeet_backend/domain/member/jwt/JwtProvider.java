package com.example.wwmeet_backend.domain.member.jwt;

import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.member.entity.SecurityMember;
import com.example.wwmeet_backend.domain.member.exception.LoginValidateException;
import com.example.wwmeet_backend.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

//    private final long expiredTime = 1000L * 60 * 60;
    private final long expiredTime = 1000L;
    private final UserDetailsService userDetailsService;
    @Value("${jwt.secret.key}")
    private String key;


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exceptionType", ErrorCode.LOGIN_INFO_EXPIRED.getMessage());
            throw new LoginValidateException(ErrorCode.LOGIN_INFO_EXPIRED);
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String createAccessToken(Member member) {
        SecurityMember securityMember = new SecurityMember(member);

        Date now = new Date();

        return Jwts.builder()
            .setSubject(securityMember.getUsername())
            .claim("authorities", securityMember.getAuthorities())
            .setExpiration(new Date(now.getTime() + expiredTime))
            .signWith(SignatureAlgorithm.HS256, key)
            .setIssuedAt(now)
            .compact();
    }

    public String createRefreshToken(){
        String refreshToken = UUID.randomUUID().toString();
        return refreshToken;
    }
}
