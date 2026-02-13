package com.kirana.kirana_register.security.refresh;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    private final RedisTemplate<String, Object> redisTemplate;

    public RefreshTokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createSession(String userId, String phone, String role) {

        String tokenId = UUID.randomUUID().toString();

        String key = buildKey(tokenId);

        redisTemplate.opsForValue().set(
                key,
                Map.of(
                        "userId", userId,
                        "phone", phone,
                        "role", role
                ),
                REFRESH_TOKEN_TTL
        );

        return tokenId;
    }

    public Map<String, Object> getSession(String tokenId) {
        return (Map<String, Object>)
                redisTemplate.opsForValue().get(buildKey(tokenId));
    }

    public void deleteSession(String tokenId) {
        redisTemplate.delete(buildKey(tokenId));
    }

    private String buildKey(String tokenId) {
        return "refresh:" + tokenId;
    }
}
