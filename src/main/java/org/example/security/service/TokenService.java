package org.example.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String BLACKLIST_TOKEN_PREFIX = "blacklist_token:";

    private final StringRedisTemplate redisTemplate;


    // Store refresh token with user details
    public void storeRefreshToken(String username, String refreshToken, long expirationInSeconds) {
        String key = REFRESH_TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, refreshToken, expirationInSeconds, TimeUnit.SECONDS);
    }

    // Get refresh token for user
    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + username);
    }

    // Delete refresh token
    public void deleteRefreshToken(String username) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
    }

    // Blacklist token
    public void blacklistToken(String token, long expirationInSeconds) {
        String key = BLACKLIST_TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, "blacklisted", expirationInSeconds, TimeUnit.SECONDS);
    }

    // Check if token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_TOKEN_PREFIX + token));
    }
}
