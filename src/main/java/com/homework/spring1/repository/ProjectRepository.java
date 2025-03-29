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
    
    List<Project> findByNameContainingIgnoreCase(String name);
    
    List<Project> findByDeadlineBefore(LocalDate date);
    
    List<Project> findByBudgetGreaterThanEqual(BigDecimal budget);
    
    @Query("SELECT p FROM Project p JOIN p.employees e WHERE e.id = :employeeId")
    List<Project> findByEmployeeId(@Param("employeeId") Long employeeId);
    
    @Query("SELECT p FROM Project p WHERE p.priority = :priority AND p.status = :status")
    List<Project> findByPriorityAndStatus(@Param("priority") String priority, @Param("status") ProjectStatus status);
} 