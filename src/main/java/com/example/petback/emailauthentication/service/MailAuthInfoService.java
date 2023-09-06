package com.example.petback.emailauthentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MailAuthInfoService {
    // javamail 인증 정보 저장
    private StringRedisTemplate redisTemplate;

    public String getData(String key) { // key를 통해 value 를 얻기
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 이메일 인증 코드 전송
    public void setDateExpire(String key, String value, long duration) {
        //duration 동안 key - value를 저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);

        //인증 코드 값을 value에 저장
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        //데이터 삭제
        redisTemplate.delete(key);
    }

    public void save(String key, String value, long duration) {
        //duration 동안 (key, value)저장
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);

        //인증 코드 값을 value에 저장
        valueOperations.set(key, value, expireDuration);
    }

    // redis 에서 getEmail을 꺼내서 사용
    public String getEmail(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

}
