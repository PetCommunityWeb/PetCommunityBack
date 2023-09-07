package com.example.petback.emailauthentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MailAuthInfoService {
    // redis에 javamail 인증 정보 저장
    private final StringRedisTemplate redisTemplate;
    private final Duration DEFAULT_EXPIRE_DURATION = Duration.ofSeconds(60);

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void save(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, DEFAULT_EXPIRE_DURATION);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public String getEmailVerificationCode(String email) {
        return getData(email);
    }

    public void setEmailVerificationCode(String email, String code) {
        save(email, code);
    }

    public void deleteEmailVerificationCode(String email) {
        deleteData(email);
    }

}
