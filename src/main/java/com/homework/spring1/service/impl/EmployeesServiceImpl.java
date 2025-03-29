package com.homework.spring1.service.impl;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;
import com.homework.spring1.repository.EmployeeRepository;
import com.homework.spring1.service.EmployeesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeesServiceImpl implements EmployeesService {
    
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        log.info("Получение списка всех сотрудников");
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(Long id) {
        log.info("Получение сотрудника с id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник с id " + id + " не найден"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "employee", key = "#result.id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee createEmployee(Employee employee) {
        log.info("Создание нового сотрудника: {}", employee.getFullName());
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "employee", key = "#id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee updateEmployee(Long id, Employee employee) {
        log.info("Обновление данных сотрудника с id: {}", id);
        if (!employeeRepository.existsById(id)) {
            log.error("Сотрудник с id {} не найден", id);
            throw new EntityNotFoundException("Сотрудник с id " + id + " не найден");
        }
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        put = { @CachePut(value = "employee", key = "#id") },
        evict = { @CacheEvict(value = "employees", allEntries = true) }
    )
    public Employee patchEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Частичное обновление данных сотрудника с id: {}", id);
        Employee existingEmployee = getEmployeeById(id);
        
        if (employeeDTO.getFullName() != null) {
            log.debug("Обновление имени сотрудника на: {}", employeeDTO.getFullName());
            existingEmployee.setFullName(employeeDTO.getFullName());
        }
        if (employeeDTO.getEmail() != null) {
            log.debug("Обновление email сотрудника на: {}", employeeDTO.getEmail());
            existingEmployee.setEmail(employeeDTO.getEmail());
        }
        if (employeeDTO.getPosition() != null) {
            log.debug("Обновление должности сотрудника на: {}", employeeDTO.getPosition());
            existingEmployee.setPosition(employeeDTO.getPosition());
        }
        if (employeeDTO.getDepartment() != null) {
            log.debug("Обновление отдела сотрудника на: {}", employeeDTO.getDepartment());
            existingEmployee.setDepartment(employeeDTO.getDepartment());
        }
        if (employeeDTO.getExperienceYears() != null) {
            log.debug("Обновление опыта работы сотрудника на: {} лет", employeeDTO.getExperienceYears());
            existingEmployee.setExperienceYears(employeeDTO.getExperienceYears());
        }

        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Caching(
        evict = {
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "employees", allEntries = true)
        }
    )
    public void deleteEmployee(Long id) {
        log.info("Удаление сотрудника с id: {}", id);
        if (!employeeRepository.existsById(id)) {
            log.error("Сотрудник с id {} не найден при попытке удаления", id);
            throw new EntityNotFoundException("Сотрудник с id " + id + " не найден");
        }
        employeeRepository.deleteById(id);
        log.info("Сотрудник с id {} успешно удален", id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "employeesByDepartment", key = "#department")
    public List<Employee> getEmployeesByDepartment(String department) {
        log.info("Получение списка сотрудников из отдела: {}", department);
        return employeeRepository.findByDepartment(department);
    }

    @Override
    @Async("taskExecutor")
    @Transactional(readOnly = true)
    public CompletableFuture<List<Employee>> findEmployeesAsync(String department) {
        log.info("Асинхронный поиск сотрудников" + (department != null ? " из отдела: " + department : ""));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Прерывание во время выполнения асинхронного запроса: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        
        List<Employee> employees;
        if (department == null || department.isEmpty()) {
            employees = employeeRepository.findAll();
        } else {
            employees = employeeRepository.findByDepartment(department);
        }
        
        log.info("Найдено {} сотрудников", employees.size());
        return CompletableFuture.completedFuture(employees);
    }
} 