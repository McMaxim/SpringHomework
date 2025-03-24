package com.homework.spring1.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limited")
@RateLimiter(name = "basicRateLimiter")
public class RateLimitedController {

    @GetMapping
    public ResponseEntity<String> rateLimitedEndpoint() {
        return ResponseEntity.ok("Этот эндпоинт имеет ограничение скорости запросов");
    }

    @GetMapping("/custom")
    @RateLimiter(name = "customRateLimiter")
    public ResponseEntity<String> customRateLimitedEndpoint() {
        return ResponseEntity.ok("Этот эндпоинт имеет специальное ограничение скорости запросов");
    }
} 