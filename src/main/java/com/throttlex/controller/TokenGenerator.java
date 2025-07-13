package com.throttlex.controller;

import com.throttlex.auth.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenGenerator {
    @GetMapping("/token")
    public String getToken(@RequestParam String userId) {
        System.out.println("Generating token for: " + userId);
        return JwtUtil.generateToken(userId);
    }
}
