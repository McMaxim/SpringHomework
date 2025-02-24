package com.homework.spring1.service.impl;

import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.service.ProjectsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectsServiceImpl extends BaseService<Project, Long> implements ProjectsService {

    public ProjectsServiceImpl() {
        create(Project.builder()
                .name("Разработка CRM системы")
                .description("Создание новой CRM системы для отдела продаж")
                .status(ProjectStatus.IN_PROGRESS)
                .deadline(LocalDate.now().plusMonths(3))
                .budget(new BigDecimal("1500000.00"))
                .employeeIds(Set.of(1L, 2L))
                .priority("HIGH")
                .build());

        create(Project.builder()
                .name("Обновление сайта компании")
                .description("Редизайн и оптимизация корпоративного сайта")
                .status(ProjectStatus.PLANNING)
                .deadline(LocalDate.now().plusMonths(2))
                .budget(new BigDecimal("800000.00"))
                .employeeIds(Set.of(2L, 3L))
                .priority("MEDIUM")
                .build());

        create(Project.builder()
                .name("Мобильное приложение")
                .description("Разработка мобильного приложения для клиентов")
                .status(ProjectStatus.TESTING)
                .deadline(LocalDate.now().plusMonths(1))
                .budget(new BigDecimal("2000000.00"))
                .employeeIds(Set.of(1L, 3L))
                .priority("HIGH")
                .build());
    }

    @Override
    protected Project createNewItem(Long id, Project project) {
        return Project.builder()
                .id(id)
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .deadline(project.getDeadline())
                .budget(project.getBudget())
                .employeeIds(project.getEmployeeIds())
                .priority(project.getPriority())
                .build();
    }

    @Override
    protected String getEntityName() {
        return "проект";
    }

    @Override
    public List<Project> getAllProjects() {
        return getAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return findById(id);
    }

    @Override
    public Project createProject(Project project) {
        return create(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        return update(id, project);
    }

    @Override
    public Project patchProject(Long id, ProjectDTO projectDTO) {
        Project existingProject = findById(id);
        
        if (projectDTO.getName() != null) {
            existingProject.setName(projectDTO.getName());
        }
        if (projectDTO.getDescription() != null) {
            existingProject.setDescription(projectDTO.getDescription());
        }
        if (projectDTO.getStatus() != null) {
            existingProject.setStatus(projectDTO.getStatus());
        }
        if (projectDTO.getDeadline() != null) {
            existingProject.setDeadline(projectDTO.getDeadline());
        }
        if (projectDTO.getBudget() != null) {
            existingProject.setBudget(projectDTO.getBudget());
        }
        if (projectDTO.getEmployeeIds() != null) {
            existingProject.setEmployeeIds(projectDTO.getEmployeeIds());
        }
        if (projectDTO.getPriority() != null) {
            existingProject.setPriority(projectDTO.getPriority());
        }

        items.put(id, existingProject);
        return existingProject;
    }

    @Override
    public void deleteProject(Long id) {
        delete(id);
    }

    @Override
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return getAll().stream()
                .filter(project -> project.getStatus() == status)
                .collect(Collectors.toList());
    }
} 