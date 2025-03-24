package com.homework.spring1;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.homework.spring1")
public class Spring1ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(Spring1ApplicationTest.class, args);
    }
} 