package com.homework.spring1.aspect;

import com.homework.spring1.Spring1Application;
import com.homework.spring1.config.TestSecurityConfig;
import com.homework.spring1.model.Employee;
import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.service.EmployeesService;
import com.homework.spring1.service.ProjectsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {Spring1Application.class, TestSecurityConfig.class})
@TestPropertySource(properties = {
    "spring.jpa.properties.hibernate.validator.apply_to_ddl=false",
    "spring.jpa.properties.jakarta.persistence.validation.mode=none"
})
@ActiveProfiles("test")
public class CountingAspectTest {

    @Autowired
    private EmployeesService employeesService;
    
    @Autowired
    private ProjectsService projectsService;
    
    @Autowired
    private CountingAspect countingAspect;
    
    @BeforeEach
    void setUp() {
        countingAspect.resetCount();
    }
    
    @Test
    void testAspectCounterIncrements() {
        Project project = Project.builder()
                .name("Тестовый проект")
                .description("Описание тестового проекта")
                .status(ProjectStatus.IN_PROGRESS)
                .priority("Высокий")
                .deadline(LocalDate.now().plusMonths(3))
                .budget(BigDecimal.valueOf(10000))
                .build();
        projectsService.createProject(project);

        Employee employee = Employee.builder()
                .fullName("Иванов Иван Иванович")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        employeesService.createEmployee(employee);

        assertEquals(4, countingAspect.getCount());
    }
} 