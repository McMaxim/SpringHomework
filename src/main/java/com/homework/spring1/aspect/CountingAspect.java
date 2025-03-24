package com.homework.spring1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class CountingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(CountingAspect.class);
    
    private final AtomicInteger counter = new AtomicInteger(0);
    
    @Around("execution(* com.homework.spring1.service.impl.*.*(..))")
    public Object countMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        counter.incrementAndGet();
        logger.debug("Счетчик увеличен перед методом: {}, текущее значение: {}", 
                joinPoint.getSignature().getName(), counter.get());
        
        try {
            Object result = joinPoint.proceed();
            
            counter.incrementAndGet();
            logger.debug("Счетчик увеличен после метода: {}, текущее значение: {}", 
                    joinPoint.getSignature().getName(), counter.get());
            
            return result;
        } catch (Throwable t) {
            counter.incrementAndGet();
            logger.debug("Счетчик увеличен после исключения в методе: {}, текущее значение: {}", 
                    joinPoint.getSignature().getName(), counter.get());
            throw t;
        }
    }
    
    public int getCount() {
        return counter.get();
    }
    
    public void resetCount() {
        counter.set(0);
    }
} 