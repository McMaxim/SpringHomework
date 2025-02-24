package com.homework.spring1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.spring1.Spring1ApplicationTest;
import com.homework.spring1.config.TestConfig;
import com.homework.spring1.model.Employee;
import com.homework.spring1.service.EmployeesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeesController.class)
@ContextConfiguration(classes = {Spring1ApplicationTest.class, TestConfig.class})
@ActiveProfiles("test")
class EmployeesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeesService employeesService;

    @Test
    void getAllEmployees_ReturnsEmployeesList() throws Exception {
        Employee employee1 = Employee.builder()
                .id(1L)
                .fullName("Иванов Иван")
                .department("Разработка")
                .build();
        Employee employee2 = Employee.builder()
                .id(2L)
                .fullName("Петров Петр")
                .department("Тестирование")
                .build();

        when(employeesService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getEmployeeById_ReturnsEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .fullName("Иванов Иван Иванович")
                .email("ivanov@company.com")
                .position("Старший разработчик")
                .department("Разработка")
                .experienceYears(5)
                .build();

        when(employeesService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Иванов Иван Иванович"));
    }

    @Test
    void createEmployee_ReturnsCreatedEmployee() throws Exception {
        Employee employeeToCreate = Employee.builder()
                .fullName("Новый Сотрудник")
                .email("new@company.com")
                .position("Аналитик")
                .department("Аналитика")
                .experienceYears(1)
                .build();

        Employee createdEmployee = Employee.builder()
                .id(1L)
                .fullName("Новый Сотрудник")
                .email("new@company.com")
                .position("Аналитик")
                .department("Аналитика")
                .experienceYears(1)
                .build();

        when(employeesService.createEmployee(any(Employee.class))).thenReturn(createdEmployee);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Новый Сотрудник"));
    }

    @Test
    void updateEmployee_ReturnsUpdatedEmployee() throws Exception {
        Employee employeeToUpdate = Employee.builder()
                .fullName("Обновленный Сотрудник")
                .email("updated@company.com")
                .position("Старший разработчик")
                .department("Разработка")
                .experienceYears(3)
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .fullName("Обновленный Сотрудник")
                .email("updated@company.com")
                .position("Старший разработчик")
                .department("Разработка")
                .experienceYears(3)
                .build();

        when(employeesService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Обновленный Сотрудник"));
    }

    @Test
    void deleteEmployee_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }
} 