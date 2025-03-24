package com.homework.spring1.service;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeesService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    Employee patchEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    List<Employee> getEmployeesByDepartment(String department);
    
    // Async method
    CompletableFuture<List<Employee>> findEmployeesAsync(String department);
} 