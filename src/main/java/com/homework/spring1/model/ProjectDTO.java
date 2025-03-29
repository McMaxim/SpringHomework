package com.homework.spring1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Название проекта обязательно для заполнения")
    @Size(min = 3, max = 100, message = "Название проекта должно быть от 3 до 100 символов")
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @NotNull(message = "Статус проекта обязателен для заполнения")
    private ProjectStatus status;

    @Future(message = "Дата дедлайна должна быть в будущем")
    private LocalDate deadline;

    @DecimalMin(value = "0.0", message = "Бюджет не может быть отрицательным")
    private BigDecimal budget;

    @NotEmpty(message = "Проект должен иметь хотя бы одного участника")
    private Set<Long> employeeIds;

    @NotBlank(message = "Приоритет проекта обязателен для заполнения")
    private String priority;

    private LocalDate startDate;
    private LocalDate endDate;
    
    public static ProjectDTO fromEntity(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setDeadline(project.getDeadline());
        dto.setBudget(project.getBudget());
        dto.setPriority(project.getPriority());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        
        if (project.getEmployees() != null) {
            dto.setEmployeeIds(project.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toSet()));
        }
        
        return dto;
    }
} 