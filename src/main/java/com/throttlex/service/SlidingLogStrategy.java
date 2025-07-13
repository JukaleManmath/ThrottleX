package com.throttlex.service;

import com.throttlex.model.RateLimitRequest;
import com.throttlex.model.UserPolicy;

import java.time.Instant;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingLogStrategy implements RateLimitStrategy{

    private final Map<String, Deque<Instant>> userLogs = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(RateLimitRequest request, UserPolicy policy) {
        String userId = request.getUserId();
        int limit = policy.getLimit();
        int windowSeconds = policy.getTimeWindowSeconds();
        Instant now = Instant.now();

        // get or initialize log for user
        userLogs.putIfAbsent(userId, new LinkedList<>());
        Deque<Instant> log = userLogs.get(userId);

        synchronized (log){
            //Remove timestamps outside the time window
            while(!log.isEmpty() && log.peekFirst().isBefore(now.minusSeconds(windowSeconds))){
                log.pollFirst();
            }
            // check limit
            if(log.size() < limit){
                log.addLast(now); //Record this request
                System.out.println("User: "+  userId + " log: "+ log.size());
                return true;
            } else{
                return false;
            }

        }
    }
}
