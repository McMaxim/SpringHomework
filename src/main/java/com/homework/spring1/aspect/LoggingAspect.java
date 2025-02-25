package com.homework.spring1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.homework.spring1.controller.*.*(..))")
    public void logMethodName(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Вызван метод: {}", methodName);
    }

    @Around("execution(* com.homework.spring1.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        
        Object result = joinPoint.proceed();
        
        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        
        logger.info("Метод {} выполнен за {} мс", 
                   joinPoint.getSignature().getName(), 
                   duration.toMillis());
        
        return result;
    }
} 