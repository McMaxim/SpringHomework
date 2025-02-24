package com.homework.spring1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Имя сотрудника обязательно для заполнения")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String fullName;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Должность обязательна для заполнения")
    @Size(min = 2, max = 50, message = "Должность должна быть от 2 до 50 символов")
    private String position;

    @NotBlank(message = "Отдел обязателен для заполнения")
    @Size(min = 2, max = 50, message = "Название отдела должно быть от 2 до 50 символов")
    private String department;

    @Min(value = 0, message = "Опыт работы не может быть отрицательным")
    private Integer experienceYears;
} 