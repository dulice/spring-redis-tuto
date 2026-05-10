package com.example.student.business;

import com.example.student.config.AppConfig;
import com.example.student.config.RedisUtil;
import com.example.student.entity.Student;
import com.example.student.exception.BusinessException;
import com.example.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentCache {
    private final RedisUtil redisUtil;
    private final StudentRepository studentRepository;
    private final AppConfig config;
    private final RedissonClient redissonClient;

    public Student getStudentCache(String email) {
        String key = redisUtil.getKey(email);
        RBucket<Student> studentCache = redissonClient.getBucket(key);

        if (studentCache.isExists()) {
            return studentCache.get();
        }
        System.out.println("--- FETCHING FROM DATABASE ---");
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Student not found."));
        studentCache.set(student);
        return student;
    }

    public void logout(String email) {
        System.out.println("--- EVICTING CACHE FOR: " + email + " ---");
        redisUtil.deleteKey(email);
    }

    public void loginCounter(String email) {
        String key = redisUtil.getKey("counter::" + email);
        RAtomicLong count = redissonClient.getAtomicLong(key);
        long currentCounter = count.incrementAndGet();
        if (currentCounter == 1) {
            count.expire(Duration.ofMinutes(config.getLoginExpireTime()));
        }
        if (currentCounter > config.getWrongCounterTime()) {
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
        RBucket<String> studentRBucket = redissonClient.getBucket(key);
        boolean isFirst = studentRBucket.setIfAbsent("1", Duration.ofSeconds(3));
        if (!isFirst) {
            throw new BusinessException("Login attempt same time.");
        }
    }

    public void releaseLock(String email) {
        String key = redisUtil.getKey("lock::" + email);
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.delete();
    }

    public Student studentSet(String studentId) {
        String rkey = redisUtil.getKey("RateLimit:" + studentId);
        if(checkIsOverLimit(rkey)) {
            throw new BusinessException("Too many request");
        }
        Student student = studentRepository.findByStudentId(studentId).orElse(null);
        String key = redisUtil.getKey("students");
        RSetCache<Student> students = redissonClient.getSetCache(key);
        students.add(student, 30, TimeUnit.SECONDS);

        return student;
    }

    public boolean checkIsOverLimit(String key) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 5, Duration.ofSeconds(30));
        boolean isAllowed = rateLimiter.tryAcquire();
        return !isAllowed;
    }

}
