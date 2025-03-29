package com.homework.spring1.repository;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.validator.apply_to_ddl=false",
    "spring.jpa.properties.jakarta.persistence.validation.mode=none"
})
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    private Employee employee;
    private Project project;
    
    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
        
        project = Project.builder()
                .name("Тестовый проект")
                .description("Описание тестового проекта")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(3))
                .budget(BigDecimal.valueOf(10000))
                .priority("Высокий")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .build();
        projectRepository.save(project);
        
        employee = Employee.builder()
                .fullName("Иванов Иван Иванович")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .projects(Set.of(project))
                .build();
    }
    
    @Test
    void shouldSaveEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getFullName()).isEqualTo("Иванов Иван Иванович");
        assertThat(savedEmployee.getEmail()).isEqualTo("ivanov@example.com");
        assertThat(savedEmployee.getPosition()).isEqualTo("Разработчик");
        assertThat(savedEmployee.getDepartment()).isEqualTo("ИТ");
        assertThat(savedEmployee.getExperienceYears()).isEqualTo(5);
        assertThat(savedEmployee.getProjects()).containsExactly(project);
    }
    
    @Test
    void shouldFindByDepartment() {
        Employee employee1 = Employee.builder()
                .fullName("Иванов Иван")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        
        Employee employee2 = Employee.builder()
                .fullName("Петров Петр")
                .email("petrov@example.com")
                .position("Аналитик")
                .department("ИТ")
                .experienceYears(3)
                .build();
        
        Employee employee3 = Employee.builder()
                .fullName("Сидоров Сидор")
                .email("sidorov@example.com")
                .position("Менеджер")
                .department("HR")
                .experienceYears(7)
                .build();
        
        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        
        List<Employee> itEmployees = employeeRepository.findByDepartment("ИТ");
        
        assertThat(itEmployees).hasSize(2);
        assertThat(itEmployees).extracting("fullName").containsExactlyInAnyOrder("Иванов Иван", "Петров Петр");
    }
    
    @Test
    void shouldFindByExperienceYearsGreaterThan() {
        Employee employee1 = Employee.builder()
                .fullName("Иванов Иван")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        
        Employee employee2 = Employee.builder()
                .fullName("Петров Петр")
                .email("petrov@example.com")
                .position("Аналитик")
                .department("ИТ")
                .experienceYears(3)
                .build();
        
        Employee employee3 = Employee.builder()
                .fullName("Сидоров Сидор")
                .email("sidorov@example.com")
                .position("Менеджер")
                .department("HR")
                .experienceYears(7)
                .build();
        
        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        
        List<Employee> experiencedEmployees = employeeRepository.findByExperienceYearsGreaterThan(4);
        
        assertThat(experiencedEmployees).hasSize(2);
        assertThat(experiencedEmployees).extracting("fullName").containsExactlyInAnyOrder("Иванов Иван", "Сидоров Сидор");
    }
    
    @Test
    void shouldFindByPositionContaining() {
        Employee employee1 = Employee.builder()
                .fullName("Иванов Иван")
                .email("ivanov@example.com")
                .position("Старший разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        
        Employee employee2 = Employee.builder()
                .fullName("Петров Петр")
                .email("petrov@example.com")
                .position("Java разработчик")
                .department("ИТ")
                .experienceYears(3)
                .build();
        
        Employee employee3 = Employee.builder()
                .fullName("Сидоров Сидор")
                .email("sidorov@example.com")
                .position("Менеджер проекта")
                .department("HR")
                .experienceYears(7)
                .build();
        
        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        
        List<Employee> developers = employeeRepository.findByPositionContaining("разработчик");
        
        assertThat(developers).hasSize(2);
        assertThat(developers).extracting("fullName").containsExactlyInAnyOrder("Иванов Иван", "Петров Петр");
    }
    
    @Test
    void shouldFindEmployeesByProjectName() {
        Project project1 = Project.builder()
                .name("Проект A")
                .description("Описание проекта A")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(3))
                .budget(BigDecimal.valueOf(10000))
                .priority("Высокий")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .build();
        
        Project project2 = Project.builder()
                .name("Проект B")
                .description("Описание проекта B")
                .status(ProjectStatus.PLANNING)
                .deadline(LocalDate.now().plusMonths(6))
                .budget(BigDecimal.valueOf(20000))
                .priority("Средний")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .build();
        
        projectRepository.saveAll(List.of(project1, project2));
        
        Employee employee1 = Employee.builder()
                .fullName("Иванов Иван")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .projects(new HashSet<>(List.of(project1)))
                .build();
        
        Employee employee2 = Employee.builder()
                .fullName("Петров Петр")
                .email("petrov@example.com")
                .position("Аналитик")
                .department("ИТ")
                .experienceYears(3)
                .projects(new HashSet<>(List.of(project1, project2)))
                .build();
        
        Employee employee3 = Employee.builder()
                .fullName("Сидоров Сидор")
                .email("sidorov@example.com")
                .position("Менеджер")
                .department("HR")
                .experienceYears(7)
                .projects(new HashSet<>(List.of(project2)))
                .build();
        
        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        
        List<Employee> projectAEmployees = employeeRepository.findEmployeesByProjectName("Проект A");
        
        assertThat(projectAEmployees).hasSize(2);
        assertThat(projectAEmployees).extracting("fullName").containsExactlyInAnyOrder("Иванов Иван", "Петров Петр");
    }
} 