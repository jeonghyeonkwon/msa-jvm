package jeonghyeon.msa.auth.security;


import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "auth::users::%s::refresh";

    public void save(String token, Long ttl) {
        redisTemplate.opsForValue().set(generateKey(token), "", Duration.ofHours(ttl));
    }

    public boolean isExist(String token) {
        System.out.println(token);
        return redisTemplate.hasKey(generateKey(token));
    }

    public boolean delete(String token) {
        return redisTemplate.delete(generateKey(token));
    }

    private String generateKey(String token) {
        return KEY_FORMAT.formatted(token);
    }

}
