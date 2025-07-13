package com.throttlex.model;

public class RateLimitRequest {
    private String userId;

    public RateLimitRequest(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
