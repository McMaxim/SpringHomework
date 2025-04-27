package app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "uuid")
//@Tag(name = "Custom Actuator Endpoint", description = "Endpoint для генерации UUID")
public class ActuatorEndpoint {

    @ReadOperation
//    @Operation(summary = "Генерация UUID", description = "Генерирует случайный UUID")
    public UUID uuidInfo() {
        return UUID.randomUUID();
    }
}