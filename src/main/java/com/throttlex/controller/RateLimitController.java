package com.throttlex.controller;

import com.throttlex.auth.JwtUtil;
import com.throttlex.model.AuditRecord;
import com.throttlex.model.UserPolicy;
import com.throttlex.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RateLimitController {
    private final RateLimitService rateLimitService = new RateLimitService();

    @PostMapping("/track")
    public ResponseEntity<String> trackRequest(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("Missing or invalid token");

        boolean allowed = rateLimitService.isRequestAllowed(userId);
        return allowed ?
                ResponseEntity.ok("✅ Request allowed") :
                ResponseEntity.status(429).body("❌ Too Many Requests");
    }

    @GetMapping("/policy")
    public ResponseEntity<UserPolicy> getPolicy(@RequestParam String userId){
        UserPolicy policy = rateLimitService.getPolicy(userId);
        return ResponseEntity.ok(policy);
    }

    @PutMapping("/policy")
    public ResponseEntity<String> updatePolicy(@RequestBody UserPolicy newPolicy, @RequestParam String userId){
        boolean updated = rateLimitService.setPolicy(userId, newPolicy);
        if(updated){
            return ResponseEntity.ok("Policy Updated..");
        }else{
            return ResponseEntity.badRequest().body("Invalid tier or Invalid Strategy....");
        }
    }

    @GetMapping("/audit")
    public ResponseEntity<List<AuditRecord>> getAuditsLogs(){
        return ResponseEntity.ok(rateLimitService.getAuditLogs());
    }
}
