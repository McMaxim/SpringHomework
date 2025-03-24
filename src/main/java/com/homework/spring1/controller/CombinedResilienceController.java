package com.homework.spring1.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import com.homework.spring1.repository.ExternalApiRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/combined")
public class CombinedResilienceController {

    private final ExternalApiRepository externalApiRepository;

    public CombinedResilienceController(ExternalApiRepository externalApiRepository) {
        this.externalApiRepository = externalApiRepository;
    }

    @GetMapping
    @CircuitBreaker(name = "combinedCircuitBreaker", fallbackMethod = "fallback")
    @RateLimiter(name = "combinedRateLimiter")
    public ResponseEntity<String> combinedResilienceEndpoint() {
        return ResponseEntity.ok(externalApiRepository.getDataWithWebClientBlocking());
    }

    public ResponseEntity<String> fallback(Exception e) {
        return ResponseEntity.ok("Комбинированный резервный ответ: Сервис недоступен или превышен лимит запросов");
    }
} 