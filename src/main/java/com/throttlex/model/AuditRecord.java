package com.throttlex.model;

import java.time.Instant;

public class AuditRecord {
    private final String userId;
    private final RateLimitStrategyType strategy;
    private final AuditStatus status;
    private final Instant timestamp;

    public AuditRecord(String userId, RateLimitStrategyType strategy,
                       AuditStatus status, Instant timestamp){
        this.userId = userId;
        this.strategy = strategy;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public RateLimitStrategyType getStrategy() {
        return strategy;
    }
}
