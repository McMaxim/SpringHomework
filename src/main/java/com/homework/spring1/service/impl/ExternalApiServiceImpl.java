package com.homework.spring1.service.impl;

import com.homework.spring1.exception.ExternalServiceException;
import com.homework.spring1.repository.ExternalApiRepository;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExternalApiServiceImpl {

    private final ExternalApiRepository externalApiRepository;
    private final AtomicInteger executionCounter = new AtomicInteger(0);

    public ExternalApiServiceImpl(ExternalApiRepository externalApiRepository) {
        this.externalApiRepository = externalApiRepository;
    }

    @Retryable(value = ExternalServiceException.class, maxAttempts = 5, backoff = @org.springframework.retry.annotation.Backoff(delay = 10000))
    public String fetchDataWithRetry(String param) {
        executionCounter.incrementAndGet();
        if (Math.random() < 0.7) {
            throw new ExternalServiceException("Внешний сервис временно недоступен");
        }
        return externalApiRepository.getDataWithRestTemplate();
    }

    @Recover
    public String recoverFetchData(ExternalServiceException e, String param) {
        return "Резервные данные после " + executionCounter.get() + " попыток: " + UUID.randomUUID();
    }
    
    public int getExecutionCount() {
        return executionCounter.get();
    }
    
    public void resetExecutionCount() {
        executionCounter.set(0);
    }
    
    public String exactlyOnceExecution(String param) {
        String uniqueKey = "execution_" + param;
        
        boolean alreadyExecuted = false;
        
        if (!alreadyExecuted) {
            try {
                String result = externalApiRepository.getDataWithWebClientBlocking();
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Ошибка выполнения", e);
            }
        } else {
            return "Операция уже была выполнена для параметра: " + param;
        }
    }
} 