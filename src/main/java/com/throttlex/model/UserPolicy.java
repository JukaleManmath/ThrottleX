package com.throttlex.model;

public class UserPolicy {
    private RateLimitStrategyType strategy;
    private int limit;
    private int timeWindowSeconds;

    public UserPolicy(RateLimitStrategyType strategy, int limit, int timeWindowSeconds){
        this.strategy = strategy;
        this.limit = limit;
        this.timeWindowSeconds = timeWindowSeconds;
    }

    public RateLimitStrategyType getStrategy() {
        return strategy;
    }

    public int getLimit() {
        return limit;
    }

    public int getTimeWindowSeconds() {
        return timeWindowSeconds;
    }
}
