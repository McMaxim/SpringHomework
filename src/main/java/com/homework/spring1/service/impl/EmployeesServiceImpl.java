package com.homework.spring1.service.impl;

import com.homework.spring1.model.Employee;
import com.homework.spring1.model.EmployeeDTO;
import com.homework.spring1.service.EmployeesService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Employee> getAllEmployees() {
        return getAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return findById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return create(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        return update(id, employee);
    }

    @Override
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
    public void deleteEmployee(Long id) {
        delete(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return getAll().stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
} 