package com.throttlex.service;

import com.throttlex.model.RateLimitStrategyType;
import org.springframework.data.redis.core.StringRedisTemplate;

public class StrategyFactory {

    private static final SlidingLogStrategy sliding = new SlidingLogStrategy();
    private static final TokenBucketStrategy token = new TokenBucketStrategy();

    // This retrieves a real bean from Spring context
    private static final StringRedisTemplate redisTemplate =
            SpringContext.getBean(StringRedisTemplate.class);

    private static final RateLimitStrategy redisFixedWindow =
            new RedisQuotaTracker(redisTemplate);

    public static RateLimitStrategy getStrategy(RateLimitStrategyType type, String userId) {
        if (userId.equals("user_distributed") && type == RateLimitStrategyType.FIXED_WINDOW) {
            return redisFixedWindow;
        }

        return switch (type) {
            case FIXED_WINDOW -> redisFixedWindow;
            case SLIDING_LOG -> sliding;
            case TOKEN_BUCKET -> token;
        };
    }
}
