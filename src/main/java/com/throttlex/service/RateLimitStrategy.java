package com.throttlex.service;

import com.throttlex.model.RateLimitRequest;
import com.throttlex.model.UserPolicy;

public interface RateLimitStrategy {
    boolean isAllowed(RateLimitRequest request, UserPolicy policy);
}
