package com.homework.spring1.service.impl;

import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectDTO;
import com.homework.spring1.model.ProjectStatus;
import com.homework.spring1.repository.EmployeeRepository;
import com.homework.spring1.repository.ProjectRepository;
import com.homework.spring1.service.ProjectsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectsServiceImpl implements ProjectsService {
    
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "projects")
    public List<Project> getAllProjects() {
        log.info("Получение списка всех проектов");
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "project", key = "#id")
    public Project getProjectById(Long id) {
        log.info("Получение проекта с id: {}", id);
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Проект с id " + id + " не найден"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "project", key = "#result.id") },
        evict = { @CacheEvict(value = "projects", allEntries = true) }
    )
    public Project createProject(Project project) {
        log.info("Создание нового проекта: {}", project.getName());
        return projectRepository.save(project);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "project", key = "#id") },
        evict = { @CacheEvict(value = "projects", allEntries = true) }
    )
    public Project updateProject(Long id, Project project) {
        log.info("Обновление данных проекта с id: {}", id);
        if (!projectRepository.existsById(id)) {
            log.error("Проект с id {} не найден", id);
            throw new EntityNotFoundException("Проект с id " + id + " не найден");
        }
        project.setId(id);
        return projectRepository.save(project);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "project", key = "#id") },
        evict = { @CacheEvict(value = "projects", allEntries = true) }
    )
    public Project patchProject(Long id, ProjectDTO projectDTO) {
        log.info("Частичное обновление данных проекта с id: {}", id);
        Project existingProject = getProjectById(id);
        
        if (projectDTO.getName() != null) {
            log.debug("Обновление названия проекта на: {}", projectDTO.getName());
            existingProject.setName(projectDTO.getName());
        }
        if (projectDTO.getDescription() != null) {
            log.debug("Обновление описания проекта");
            existingProject.setDescription(projectDTO.getDescription());
        }
        if (projectDTO.getStartDate() != null) {
            log.debug("Обновление даты начала проекта на: {}", projectDTO.getStartDate());
            existingProject.setStartDate(projectDTO.getStartDate());
        }
        if (projectDTO.getEndDate() != null) {
            log.debug("Обновление даты окончания проекта на: {}", projectDTO.getEndDate());
            existingProject.setEndDate(projectDTO.getEndDate());
        }

        return projectRepository.save(existingProject);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        evict = {
            @CacheEvict(value = "project", key = "#id"),
            @CacheEvict(value = "projects", allEntries = true)
        }
    )
    public void deleteProject(Long id) {
        log.info("Удаление проекта с id: {}", id);
        if (!projectRepository.existsById(id)) {
            log.error("Проект с id {} не найден при попытке удаления", id);
            throw new EntityNotFoundException("Проект с id " + id + " не найден");
        }
        projectRepository.deleteById(id);
        log.info("Проект с id {} успешно удален", id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "projectsByStatus", key = "#status")
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }
} 