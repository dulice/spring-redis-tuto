package com.example.student.business;

import com.example.student.config.AppConfig;
import com.example.student.config.RedisUtil;
import com.example.student.entity.Student;
import com.example.student.exception.BusinessException;
import com.example.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentCache {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisUtil redisUtil;
    private final StudentRepository studentRepository;
    private final AppConfig config;

    public Student getStudentCache(String email) {
        String key = redisUtil.getKey(email);

        Object obj = redisTemplate.opsForValue().get(key);
        ObjectMapper objectMapper = new ObjectMapper();
        Student studentCache = objectMapper.convertValue(obj, Student.class);
        if(studentCache != null) {
            return studentCache;
        }
        System.out.println("--- FETCHING FROM DATABASE ---");
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Student not found."));
        redisTemplate.opsForValue().set(key, student);
        return student;
    }

    public void logout(String email) {
        System.out.println("--- EVICTING CACHE FOR: " + email + " ---");
        redisUtil.deleteKey(email);
    }

    public void loginCounter(String email) {
        String key = redisUtil.getKey("counter::" + email);
        Long count = redisTemplate.opsForValue().increment(key);
        if(count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(config.getLoginExpireTime()));
        }
        if(count > config.getWrongCounterTime()) {
            throw new BusinessException("Too many times wrong pwd.");
        }
    }

    public void resetCounter(String email) {
        String key = "counter::" + email;
        redisUtil.deleteKey(key);
    }

    //prevent login request at the same time
    public void checkDuplicateLogin(String email) {
        String key = redisUtil.getKey("lock::" + email);
        boolean isFirst = redisTemplate.opsForValue().setIfAbsent(key, "1", 5, TimeUnit.SECONDS);
        if(!isFirst) {
            throw new BusinessException("Login attempt same time.");
        }
    }

    public void releaseLock(String email) {
        String key = redisUtil.getKey("lock::" + email);
        redisTemplate.delete(key);
    }

}
