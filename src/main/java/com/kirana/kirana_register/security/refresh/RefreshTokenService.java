package com.kirana.kirana_register.security.refresh;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RefreshTokenService(
            RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public String createSession(String userId, String phone, String role) {

        String tokenId = UUID.randomUUID().toString();
        String key = buildKey(tokenId);

        try {
            String json = objectMapper.writeValueAsString(
                    Map.of(
                            "userId", userId,
                            "phone", phone,
                            "role", role
                    )
            );

            redisTemplate.opsForValue().set(
                    key,
                    json,
                    REFRESH_TOKEN_TTL
            );

        } catch (Exception e) {
            throw new IllegalStateException("Failed to store refresh session", e);
        }

        return tokenId;
    }

    public Map<String, Object> getSession(String tokenId) {

        String json = redisTemplate.opsForValue().get(buildKey(tokenId));

        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<Map<String, Object>>() {}
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read refresh session", e);
        }
    }

    public void deleteSession(String tokenId) {
        redisTemplate.delete(buildKey(tokenId));
    }

    private String buildKey(String tokenId) {
        return "refresh:" + tokenId;
    }
}
