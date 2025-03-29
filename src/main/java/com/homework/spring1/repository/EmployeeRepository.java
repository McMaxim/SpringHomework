package com.homework.spring1.repository;

import com.homework.spring1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByDepartment(String department);
    
    List<Employee> findByPositionContaining(String position);
    
    List<Employee> findByExperienceYearsGreaterThan(Integer experienceYears);
    
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);
    
    @Query("SELECT e FROM Employee e JOIN e.projects p WHERE p.name = :projectName")
    List<Employee> findEmployeesByProjectName(@Param("projectName") String projectName);
} 