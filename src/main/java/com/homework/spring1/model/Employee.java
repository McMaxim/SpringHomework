package com.homework.spring1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Имя сотрудника обязательно для заполнения")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Некорректный формат email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Должность обязательна для заполнения")
    @Size(min = 2, max = 50, message = "Должность должна быть от 2 до 50 символов")
    @Column(name = "position", nullable = false, length = 50)
    private String position;

    @NotBlank(message = "Отдел обязателен для заполнения")
    @Size(min = 2, max = 50, message = "Название отдела должно быть от 2 до 50 символов")
    @Column(name = "department", nullable = false, length = 50)
    private String department;

    @Min(value = 0, message = "Опыт работы не может быть отрицательным")
    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_employees",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

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