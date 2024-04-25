package com.example.wwmeet_backend.domain.member.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final String KEY_PREFIX = "refreshToken";
    private final Long REFRESH_TOKEN_EXPIRED = 30L; // ToDo: Day로 이름 바꾸기
    private final RedisTemplate redisTemplate;

    public void save(String refreshToken){
        redisTemplate.opsForValue().set(KEY_PREFIX + refreshToken, true);
        redisTemplate.expire(KEY_PREFIX + refreshToken, REFRESH_TOKEN_EXPIRED, TimeUnit.DAYS);
    }

    public Optional<Boolean> findByRefreshToken(String refreshToken) {
        Boolean hasToken = (Boolean) redisTemplate.opsForValue().get(KEY_PREFIX + refreshToken);
        if(hasToken == null){
            return Optional.empty();
        }
        return Optional.of(hasToken);
    }
}
