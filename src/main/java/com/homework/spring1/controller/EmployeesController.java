package com.homework.spring1.controller;

import com.homework.spring1.api.EmployeesApi;
import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;
import com.homework.spring1.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeesController implements EmployeesApi {

    private final EmployeesService employeesService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeesService.getAllEmployees());
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Long id) {
        return ResponseEntity.ok(employeesService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeesService.createEmployee(employee));
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) {
        return ResponseEntity.ok(employeesService.updateEmployee(id, employee));
    }

    @Override
    public ResponseEntity<Employee> patchEmployee(Long id, EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeesService.patchEmployee(id, employeeDTO));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        employeesService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
} 