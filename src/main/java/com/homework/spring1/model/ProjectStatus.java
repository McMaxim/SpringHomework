package com.homework.spring1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonFormat(shape = JsonFormat.Shape.STRING)
@Schema(description = "Статусы жизненного цикла проекта")
public enum ProjectStatus {
    @Schema(description = "Проект на стадии планирования")
    PLANNING,

    @Schema(description = "Проект в активной разработке")
    IN_PROGRESS,

    @Schema(description = "Проект на стадии тестирования")
    TESTING,

    @Schema(description = "Проект завершен")
    COMPLETED,

    @Schema(description = "Проект приостановлен")
    ON_HOLD,

    @Schema(description = "Проект отменен")
    CANCELLED;

    public static String getAvailableStatuses() {
        StringBuilder sb = new StringBuilder("Доступные статусы:\n");
        for (ProjectStatus status : ProjectStatus.values()) {
            sb.append("- ").append(status.name()).append(": ").append(getDescription(status)).append("\n");
        }
        return sb.toString();
    }

    private static String getDescription(ProjectStatus status) {
        return switch (status) {
            case PLANNING -> "Проект на стадии планирования";
            case IN_PROGRESS -> "Проект в активной разработке";
            case TESTING -> "Проект на стадии тестирования";
            case COMPLETED -> "Проект завершен";
            case ON_HOLD -> "Проект приостановлен";
            case CANCELLED -> "Проект отменен";
        };
    }
} 