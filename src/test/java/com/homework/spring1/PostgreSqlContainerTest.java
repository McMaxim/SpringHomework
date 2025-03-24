package com.homework.spring1;

import com.homework.spring1.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(classes = {Spring1Application.class, TestSecurityConfig.class})
public class PostgreSqlContainerTest {

    private static final Logger logger = LoggerFactory.getLogger(PostgreSqlContainerTest.class);

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("init.sql");

    @Test
    void testPostgresConnection() {
        logger.info("Хост контейнера PostgreSQL: {}", postgres.getHost());
        logger.info("Порт контейнера PostgreSQL: {}", postgres.getFirstMappedPort());
        logger.info("URL подключения к JDBC PostgreSQL: {}", postgres.getJdbcUrl());
        
        assertTrue(postgres.isRunning());
    }
} 