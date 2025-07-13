package com.throttlex.service;

import com.throttlex.model.RateLimitRequest;
import com.throttlex.model.UserPolicy;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketStrategy implements RateLimitStrategy{

    //container to store current token value and lastRefillTime of the bucket
    static class TokenBucket{
        double tokens;
        Instant lastRefillTime;

        public TokenBucket(double tokens, Instant lastRefillTime){
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }

    private final Map<String, TokenBucket> userBuckets = new ConcurrentHashMap<>();


    @Override
    public boolean isAllowed(RateLimitRequest request, UserPolicy policy) {
        String userId = request.getUserId();
        int capacity = policy.getLimit();
        int refillInterval = policy.getTimeWindowSeconds(); // total refill time
        double refillRate = (double) capacity / refillInterval; // tokens per second

        Instant now = Instant.now();
        userBuckets.putIfAbsent(userId, new TokenBucket(capacity, now));
        TokenBucket bucket = userBuckets.get(userId);

        synchronized (bucket){
            long elapsed = now.getEpochSecond() - bucket.lastRefillTime.getEpochSecond();
            double refillTokens = elapsed * refillRate;

            if(refillTokens > 0){
                bucket.tokens = Math.min(capacity, bucket.tokens + refillTokens);
                bucket.lastRefillTime = now;
            }

            if(bucket.tokens >= 1){
                bucket.tokens -= 1;
                return true;
            } else{
                return false;
            }

        }
    }
}
