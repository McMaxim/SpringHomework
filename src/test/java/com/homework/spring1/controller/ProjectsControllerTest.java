package com.homework.spring1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.spring1.Spring1ApplicationTest;
import com.homework.spring1.config.TestConfig;
import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.service.ProjectsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectsController.class)
@ContextConfiguration(classes = {Spring1ApplicationTest.class, TestConfig.class})
@ActiveProfiles("test")
class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectsService projectsService;

    @Test
    void getAllProjects_ReturnsProjectsList() throws Exception {
        Project project1 = Project.builder()
                .id(1L)
                .name("Проект 1")
                .status(ProjectStatus.IN_PROGRESS)
                .build();
        Project project2 = Project.builder()
                .id(2L)
                .name("Проект 2")
                .status(ProjectStatus.PLANNING)
                .build();

        when(projectsService.getAllProjects()).thenReturn(Arrays.asList(project1, project2));

        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getProjectById_ReturnsProject() throws Exception {
        Project project = Project.builder()
                .id(1L)
                .name("Тестовый проект")
                .description("Описание")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(1))
                .budget(new BigDecimal("1000000.00"))
                .employeeIds(Set.of(1L, 2L))
                .priority("HIGH")
                .build();

        when(projectsService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/v1/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Тестовый проект"));
    }

    @Test
    void createProject_ReturnsCreatedProject() throws Exception {
        Project projectToCreate = Project.builder()
                .name("Новый проект")
                .description("Описание нового проекта")
                .status(ProjectStatus.PLANNING)
                .deadline(LocalDate.now().plusMonths(1))
                .budget(new BigDecimal("500000.00"))
                .employeeIds(Set.of(1L, 2L))
                .priority("MEDIUM")
                .build();

        Project createdProject = Project.builder()
                .id(1L)
                .name("Новый проект")
                .description("Описание нового проекта")
                .status(ProjectStatus.PLANNING)
                .deadline(LocalDate.now().plusMonths(1))
                .budget(new BigDecimal("500000.00"))
                .employeeIds(Set.of(1L, 2L))
                .priority("MEDIUM")
                .build();

        when(projectsService.createProject(any(Project.class))).thenReturn(createdProject);

        mockMvc.perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Новый проект"));
    }

    @Test
    void updateProject_ReturnsUpdatedProject() throws Exception {
        Project projectToUpdate = Project.builder()
                .name("Обновленный проект")
                .description("Обновленное описание проекта")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(2))
                .budget(new BigDecimal("600000.00"))
                .employeeIds(Set.of(2L, 3L))
                .priority("HIGH")
                .build();

        Project updatedProject = Project.builder()
                .id(1L)
                .name("Обновленный проект")
                .description("Обновленное описание проекта")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(2))
                .budget(new BigDecimal("600000.00"))
                .employeeIds(Set.of(2L, 3L))
                .priority("HIGH")
                .build();

        when(projectsService.updateProject(eq(1L), any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Обновленный проект"));
    }

    @Test
    void patchProject_ReturnsUpdatedProject() throws Exception {
        ProjectDTO projectToPatch = ProjectDTO.builder()
                .name("Частично обновленный проект")
                .status(ProjectStatus.IN_PROGRESS)
                .build();

        Project patchedProject = Project.builder()
                .id(1L)
                .name("Частично обновленный проект")
                .description("Старое описание")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(1))
                .budget(new BigDecimal("1000000.00"))
                .employeeIds(Set.of(1L, 2L))
                .priority("HIGH")
                .build();

        when(projectsService.patchProject(eq(1L), any(ProjectDTO.class))).thenReturn(patchedProject);

        mockMvc.perform(patch("/api/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectToPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Частично обновленный проект"))
                .andExpect(jsonPath("$.description").value("Старое описание"));
    }

    @Test
    void deleteProject_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/projects/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProjectsByStatus_ReturnsFilteredProjects() throws Exception {
        Project project = Project.builder()
                .id(1L)
                .name("Проект в работе")
                .status(ProjectStatus.IN_PROGRESS)
                .build();

        when(projectsService.getProjectsByStatus(ProjectStatus.IN_PROGRESS))
                .thenReturn(Arrays.asList(project));

        mockMvc.perform(get("/api/v1/projects/status/IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("IN_PROGRESS"));
    }

    @Test
    void getAllProjectStatuses_ReturnsAllStatuses() throws Exception {
        mockMvc.perform(get("/api/v1/projects/statuses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("PLANNING"))
                .andExpect(jsonPath("$[0].description").value("Проект на стадии планирования"))
                .andExpect(jsonPath("$[1].code").value("IN_PROGRESS"))
                .andExpect(jsonPath("$[1].description").value("Проект в активной разработке"))
                .andExpect(jsonPath("$[2].code").value("TESTING"))
                .andExpect(jsonPath("$[2].description").value("Проект на стадии тестирования"))
                .andExpect(jsonPath("$[3].code").value("COMPLETED"))
                .andExpect(jsonPath("$[3].description").value("Проект завершен"))
                .andExpect(jsonPath("$[4].code").value("ON_HOLD"))
                .andExpect(jsonPath("$[4].description").value("Проект приостановлен"))
                .andExpect(jsonPath("$[5].code").value("CANCELLED"))
                .andExpect(jsonPath("$[5].description").value("Проект отменен"));
    }
} 