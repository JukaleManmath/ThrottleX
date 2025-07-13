package com.throttlex.service;

import com.throttlex.model.RateLimitRequest;
import com.throttlex.model.UserPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

public class RedisQuotaTracker implements  RateLimitStrategy{
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisQuotaTracker(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        System.out.println("✅ RedisQuotaTracker initialized with redisTemplate = " + redisTemplate);
    }

    @Override
    public boolean isAllowed(RateLimitRequest request, UserPolicy policy){
        System.out.println("🔍 Redis Host Connection Attempt: " + redisTemplate);
        String key = "quota:" + request.getUserId();
        Long count  = redisTemplate.opsForValue().increment(key);

        if(count == 1){
            // first request -> set TTl for the key
            redisTemplate.expire(key, Duration.ofSeconds(policy.getTimeWindowSeconds()));
        }
        return count <= policy.getLimit();
    }
}
