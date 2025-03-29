package com.homework.spring1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.spring1.api.ProjectsApi;
import com.homework.spring1.config.TestSecurityConfig;
import com.homework.spring1.model.Employee;
import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.service.ProjectsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectsController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.jpa.properties.hibernate.validator.apply_to_ddl=false",
    "spring.jpa.properties.jakarta.persistence.validation.mode=none"
})
class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectsService projectsService;

    @Test
    void getAllProjects_ReturnsListOfProjects() throws Exception {
        Project project = createTestProject();
        List<Project> projects = Collections.singletonList(project);

        when(projectsService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Тестовый проект")))
                .andExpect(jsonPath("$[0].description", is("Описание тестового проекта")));
    }

    @Test
    void getProjectById_ReturnsProject() throws Exception {
        Project project = createTestProject();

        when(projectsService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Тестовый проект")))
                .andExpect(jsonPath("$.description", is("Описание тестового проекта")));
    }

    @Test
    void createProject_ReturnsCreatedProject() throws Exception {
        Project projectToCreate = createTestProject();
        projectToCreate.setId(null);

        Employee employee = Employee.builder()
                .id(1L)
                .fullName("Иванов Иван Иванович")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        Set<Employee> employees = new HashSet<>();
        employees.add(employee);
        projectToCreate.setEmployees(employees);

        Project createdProject = createTestProject();

        when(projectsService.createProject(any(Project.class))).thenReturn(createdProject);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Тестовый проект")))
                .andExpect(jsonPath("$.description", is("Описание тестового проекта")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.priority", is("Высокий")));
    }

    @Test
    void updateProject_ReturnsUpdatedProject() throws Exception {
        Project projectToUpdate = createTestProject();
        
        Employee employee = Employee.builder()
                .id(1L)
                .fullName("Иванов Иван Иванович")
                .email("ivanov@example.com")
                .position("Разработчик")
                .department("ИТ")
                .experienceYears(5)
                .build();
        Set<Employee> employees = new HashSet<>();
        employees.add(employee);
        projectToUpdate.setEmployees(employees);
        
        Project updatedProject = createTestProject();
        updatedProject.setName("Обновленный проект");
        updatedProject.setDescription("Обновленное описание");

        when(projectsService.updateProject(eq(1L), any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Обновленный проект")))
                .andExpect(jsonPath("$.description", is("Обновленное описание")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.priority", is("Высокий")));
    }

    @Test
    void patchProject_ReturnsUpdatedProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Обновленный проект");
        projectDTO.setDescription("Обновленное описание");
        projectDTO.setStatus(ProjectStatus.IN_PROGRESS);
        projectDTO.setPriority("Высокий");
        projectDTO.setDeadline(LocalDate.now().plusMonths(1));
        projectDTO.setBudget(BigDecimal.valueOf(15000));
        projectDTO.setEmployeeIds(Set.of(1L));

        Project updatedProject = createTestProject();
        updatedProject.setName("Обновленный проект");
        updatedProject.setDescription("Обновленное описание");

        when(projectsService.patchProject(eq(1L), any(ProjectDTO.class))).thenReturn(updatedProject);

        mockMvc.perform(patch("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Обновленный проект")))
                .andExpect(jsonPath("$.description", is("Обновленное описание")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.priority", is("Высокий")));
    }

    @Test
    void deleteProject_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProjectsByStatus_ReturnsFilteredProjects() throws Exception {
        Project project = createTestProject();
        List<Project> filteredProjects = Collections.singletonList(project);

        when(projectsService.getProjectsByStatus(ProjectStatus.IN_PROGRESS))
                .thenReturn(filteredProjects);

        mockMvc.perform(get("/api/v1/projects/status/IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Тестовый проект"));
    }

    @Test
    void getAllProjectStatuses_ReturnsAllStatuses() throws Exception {
        mockMvc.perform(get("/api/v1/projects/statuses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("PLANNING"))
                .andExpect(jsonPath("$[0].description").value("Проект на стадии планирования"))
                .andExpect(jsonPath("$[5].code").value("CANCELLED"))
                .andExpect(jsonPath("$[5].description").value("Проект отменен"));
    }

    private Project createTestProject() {
        Set<Long> employeeIds = new HashSet<>();
        employeeIds.add(1L);

        return Project.builder()
                .id(1L)
                .name("Тестовый проект")
                .description("Описание тестового проекта")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(3))
                .budget(BigDecimal.valueOf(10000))
                .priority("Высокий")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .build();
    }
} 