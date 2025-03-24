package com.homework.spring1.aspect;

import com.homework.spring1.Spring1Application;
import com.homework.spring1.config.TestSecurityConfig;
import com.homework.spring1.model.Employee;
import com.homework.spring1.service.EmployeesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {Spring1Application.class, TestSecurityConfig.class})
@ActiveProfiles("test")
public class CountingAspectTest {

    @Autowired
    private CountingAspect countingAspect;
    
    @Autowired
    private EmployeesService employeesService;
    
    @BeforeEach
    public void setup() {
        countingAspect.resetCount();
    }
    
    @Test
    public void testAspectCounterIncrements() {
        assertEquals(0, countingAspect.getCount());
        
        Employee employee = employeesService.getEmployeeById(1L);
        assertNotNull(employee);
        
        assertEquals(2, countingAspect.getCount());
        
        employeesService.getAllEmployees();
        
        assertEquals(4, countingAspect.getCount());
    }
} 