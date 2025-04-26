package com.homework.spring1.repository;

import com.homework.spring1.model.Project;
import com.homework.spring1.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByStatus(ProjectStatus status);
} 