package com.example.student.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String redisKeyPrefix = "std";

    public String getKey(String key) {
        return redisKeyPrefix + "::" + key;
    }

    public void deleteKey(String key) {
        String prefixKey = getKey(key);
        redisTemplate.delete(prefixKey);
    }
}
