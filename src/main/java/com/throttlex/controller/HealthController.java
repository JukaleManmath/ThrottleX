package com.throttlex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String getHealth(){
        return "ThrottleX is UP and running";
    }
}
