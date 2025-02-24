package com.homework.spring1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.homework.spring1")
public class Spring1ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(Spring1ApplicationTest.class, args);
    }
} 