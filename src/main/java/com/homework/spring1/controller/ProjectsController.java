package com.homework.spring1.controller;

import com.homework.spring1.api.ProjectsApi;
import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.model.ProjectStatusInfo;
import com.homework.spring1.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProjectsController implements ProjectsApi {

    private final ProjectsService projectsService;

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectsService.getAllProjects());
    }

    @Override
    public ResponseEntity<Project> getProjectById(Long id) {
        return ResponseEntity.ok(projectsService.getProjectById(id));
    }

    @Override
    public ResponseEntity<Project> createProject(Project project) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectsService.createProject(project));
    }

    @Override
    public ResponseEntity<Project> updateProject(Long id, Project project) {
        return ResponseEntity.ok(projectsService.updateProject(id, project));
    }

    @Override
    public ResponseEntity<Project> patchProject(Long id, ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectsService.patchProject(id, projectDTO));
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        projectsService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Project>> getProjectsByStatus(String status) {
        try {
            ProjectStatus projectStatus = ProjectStatus.valueOf(status.toUpperCase());
            return ResponseEntity.ok(projectsService.getProjectsByStatus(projectStatus));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверный статус проекта. " + ProjectStatus.getAvailableStatuses());
        }
    }

    @Override
    public ResponseEntity<List<ProjectStatusInfo>> getAllProjectStatuses() {
        List<ProjectStatusInfo> statuses = Arrays.stream(ProjectStatus.values())
                .map(status -> ProjectStatusInfo.builder()
                        .code(status.name())
                        .description(switch (status) {
                            case PLANNING -> "Проект на стадии планирования";
                            case IN_PROGRESS -> "Проект в активной разработке";
                            case TESTING -> "Проект на стадии тестирования";
                            case COMPLETED -> "Проект завершен";
                            case ON_HOLD -> "Проект приостановлен";
                            case CANCELLED -> "Проект отменен";
                        })
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(statuses);
    }
} 