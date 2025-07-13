package com.throttlex.service;

import com.throttlex.model.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimitService {
    private final FixedWindowStrategy fixedWindowStrategy = new FixedWindowStrategy();
    private final AuditLogger auditLogger = new AuditLogger();

    public List<AuditRecord> getAuditLogs(){
        return auditLogger.getRecentLogs();
    }

    // Dummy hardcoded policy store
    private final Map<String, UserPolicy> userPolicies = new HashMap<>();

    public UserPolicy getPolicy(String userId){
        return userPolicies.get(userId);
    }

    public boolean setPolicy(String userId, UserPolicy newPolicy){
        if(newPolicy.getLimit() <= 0 || newPolicy.getTimeWindowSeconds() <= 0){
            return false;
        }
        userPolicies.put(userId,newPolicy);
        return true;
    }

    public RateLimitService(){
        userPolicies.put("user_free", new UserPolicy(RateLimitStrategyType.FIXED_WINDOW,5,60));
        userPolicies.put("user_pro", new UserPolicy(RateLimitStrategyType.FIXED_WINDOW, 100, 60));
        userPolicies.put("user_sliding", new UserPolicy(RateLimitStrategyType.SLIDING_LOG,5,60));
        userPolicies.put("user_token", new UserPolicy(RateLimitStrategyType.TOKEN_BUCKET, 5, 10));
        userPolicies.put("user_distributed", new UserPolicy(RateLimitStrategyType.FIXED_WINDOW,5, 60));
    }

    public boolean isRequestAllowed(String userId){
        UserPolicy policy = userPolicies.getOrDefault(userId, new UserPolicy(RateLimitStrategyType.FIXED_WINDOW,5,60));
        RateLimitRequest request = new RateLimitRequest(userId);

        RateLimitStrategy strategy = StrategyFactory.getStrategy(policy.getStrategy(), userId);
        boolean allowed = strategy.isAllowed(request, policy);
        AuditStatus status = allowed ? AuditStatus.ACCEPT: AuditStatus.REJECT;
        auditLogger.log(new AuditRecord(userId, policy.getStrategy(), status, Instant.now()));
        return allowed;
    }
}
