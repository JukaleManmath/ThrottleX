package com.throttlex.service;

import com.throttlex.model.QuotaRecord;
import com.throttlex.model.RateLimitRequest;
import com.throttlex.model.UserPolicy;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FixedWindowStrategy implements RateLimitStrategy {
    private final Map<String, QuotaRecord> userQuotaMap = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(RateLimitRequest request, UserPolicy policy){
        String userId = request.getUserId();
        QuotaRecord record = userQuotaMap.get(userId);
        Instant now = Instant.now();

        if(record == null ||
        now.isAfter(record.getWindowStart().plusSeconds(policy.getTimeWindowSeconds()))){
            record = new QuotaRecord(1, now);
            userQuotaMap.put(userId, record);
            return true;
        }

        if(record.getRequestCount().incrementAndGet() <= policy.getLimit()){
            return true;
        }

        return false;
    }

}
