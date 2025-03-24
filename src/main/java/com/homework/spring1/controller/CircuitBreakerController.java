package com.homework.spring1.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.homework.spring1.repository.ExternalApiRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/circuit-breaker")
@CircuitBreaker(name = "backendACircuitBreaker", fallbackMethod = "fallback")
public class CircuitBreakerController {

    private final ExternalApiRepository externalApiRepository;

    public CircuitBreakerController(ExternalApiRepository externalApiRepository) {
        this.externalApiRepository = externalApiRepository;
    }

    @GetMapping
    public ResponseEntity<String> circuitBreakerEndpoint() {
        return ResponseEntity.ok(externalApiRepository.getDataWithRestTemplate());
    }

    public ResponseEntity<String> fallback(Exception e) {
        return ResponseEntity.ok("Резервный ответ прерывателя цепи: Сервис временно недоступен");
    }
} 