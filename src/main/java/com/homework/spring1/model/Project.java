package com.homework.spring1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Название проекта обязательно для заполнения")
    @Size(min = 3, max = 100, message = "Название проекта должно быть от 3 до 100 символов")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Статус проекта обязателен для заполнения")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @Future(message = "Дата дедлайна должна быть в будущем")
    @Column(name = "deadline")
    private LocalDate deadline;

    @DecimalMin(value = "0.0", message = "Бюджет не может быть отрицательным")
    @Column(precision = 19, scale = 2)
    private BigDecimal budget;

    @NotEmpty(message = "Проект должен иметь хотя бы одного участника")
    @ManyToMany
    @JoinTable(
        name = "project_employees",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();
    
    @Transient
    private Set<Long> employeeIds;

    @NotBlank(message = "Приоритет проекта обязателен для заполнения")
    @Column(nullable = false)
    private String priority;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectStatusInfo> statusHistory = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 