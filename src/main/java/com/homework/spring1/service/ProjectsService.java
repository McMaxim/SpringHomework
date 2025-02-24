package com.homework.spring1.service;

import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;

import java.util.List;

public interface ProjectsService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    Project patchProject(Long id, ProjectDTO projectDTO);
    void deleteProject(Long id);
    List<Project> getProjectsByStatus(ProjectStatus status);
} 