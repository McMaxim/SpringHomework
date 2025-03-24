package com.homework.spring1.service.impl;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;
import com.homework.spring1.service.EmployeesService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class EmployeesServiceImpl extends BaseService<Employee, Long> implements EmployeesService {

    public EmployeesServiceImpl() {
        create(Employee.builder()
                .fullName("Иванов Иван Иванович")
                .email("ivanov@company.com")
                .position("Старший разработчик")
                .department("Разработка")
                .experienceYears(5)
                .build());

        create(Employee.builder()
                .fullName("Петрова Анна Сергеевна")
                .email("petrova@company.com")
                .position("Бизнес-аналитик")
                .department("Аналитика")
                .experienceYears(3)
                .build());

        create(Employee.builder()
                .fullName("Сидоров Петр Михайлович")
                .email("sidorov@company.com")
                .position("Тестировщик")
                .department("Тестирование")
                .experienceYears(2)
                .build());
    }

    @Override
    protected Employee createNewItem(Long id, Employee employee) {
        return Employee.builder()
                .id(id)
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .department(employee.getDepartment())
                .experienceYears(employee.getExperienceYears())
                .build();
    }

    @Override
    protected String getEntityName() {
        return "сотрудник";
    }

    @Override
    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        return getAll();
    }

    @Override
    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(Long id) {
        return findById(id);
    }

    @Override
    @Caching(
        put = { @CachePut(value = "employee", key = "#result.id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee createEmployee(Employee employee) {
        return create(employee);
    }

    @Override
    @Caching(
        put = { @CachePut(value = "employee", key = "#id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee updateEmployee(Long id, Employee employee) {
        return update(id, employee);
    }

    @Override
    @Caching(
        put = { @CachePut(value = "employee", key = "#id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee patchEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = findById(id);
        
        if (employeeDTO.getFullName() != null) {
            existingEmployee.setFullName(employeeDTO.getFullName());
        }
        if (employeeDTO.getEmail() != null) {
            existingEmployee.setEmail(employeeDTO.getEmail());
        }
        if (employeeDTO.getPosition() != null) {
            existingEmployee.setPosition(employeeDTO.getPosition());
        }
        if (employeeDTO.getDepartment() != null) {
            existingEmployee.setDepartment(employeeDTO.getDepartment());
        }
        if (employeeDTO.getExperienceYears() != null) {
            existingEmployee.setExperienceYears(employeeDTO.getExperienceYears());
        }

        items.put(id, existingEmployee);
        return existingEmployee;
    }

    @Override
    @Caching(
        evict = {
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "employees", allEntries = true)
        }
    )
    public void deleteEmployee(Long id) {
        delete(id);
    }

    @Override
    @Cacheable(value = "employeesByDepartment", key = "#department")
    public List<Employee> getEmployeesByDepartment(String department) {
        return getAll().stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Employee>> findEmployeesAsync(String department) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<Employee> employees = getAll().stream()
                .filter(employee -> {
                    if (department == null || department.isEmpty()) {
                        return true;
                    }
                    return employee.getDepartment().equalsIgnoreCase(department);
                })
                .collect(Collectors.toList());
                
        return CompletableFuture.completedFuture(employees);
    }
} 