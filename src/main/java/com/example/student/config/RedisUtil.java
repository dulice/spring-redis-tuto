package com.example.student.config;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    private final String redisKeyPrefix = "std";
    private final RedissonClient redissonClient;

    public String getKey(String key) {
        return redisKeyPrefix + "::" + key;
    }

    public void deleteKey(String key) {
        String prefixKey = getKey(key);
        redissonClient.getKeys().delete(prefixKey);
    }
}
