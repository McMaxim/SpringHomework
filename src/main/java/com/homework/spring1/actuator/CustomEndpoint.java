package com.homework.spring1.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Component
@Endpoint(id = "custom-uuid")
@Tag(name = "Custom Actuator Endpoint", description = "Endpoint для генерации UUID")
public class CustomEndpoint {

    @ReadOperation
    @Operation(summary = "Генерация UUID", description = "Генерирует случайный UUID")
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
} 