package com.homework.spring1.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    @Size(min = 3, max = 100, message = "Название проекта должно быть от 3 до 100 символов")
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    private ProjectStatus status;

    @Future(message = "Дата дедлайна должна быть в будущем")
    private LocalDate deadline;

    @DecimalMin(value = "0.0", message = "Бюджет не может быть отрицательным")
    private BigDecimal budget;

    private Set<Long> employeeIds;

    @Pattern(regexp = "^(HIGH|MEDIUM|LOW)$", message = "Приоритет должен быть HIGH, MEDIUM или LOW")
    private String priority;
} 