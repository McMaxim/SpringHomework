package com.homework.audit.service;

import com.homework.audit.model.AuditEventType;
import com.homework.audit.model.UserAudit;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration")
@Tag("integration")
@TestPropertySource(properties = {
    "spring.data.cassandra.contact-points=${CASSANDRA_HOST:127.0.0.1}",
    "spring.data.cassandra.port=${CASSANDRA_PORT:9042}",
    "spring.data.cassandra.keyspace-name=${CASSANDRA_KEYSPACE:audit_keyspace}",
    "spring.data.cassandra.local-datacenter=${CASSANDRA_DATACENTER:datacenter1}"
})
@EnabledIfSystemProperty(named = "runIntegrationTests", matches = "true")
class UserAuditServiceIntegrationTest {

    @Autowired
    private UserAuditService userAuditService;

    @Test
    void createAndRetrieveAuditEvent() {
        UUID userId = UUID.randomUUID();
        AuditEventType eventType = AuditEventType.INSERT;
        String eventDetails = "User created a new record";
        
        UserAudit result = userAuditService.createAuditEvent(userId, eventType, eventDetails);
        
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(eventType.name(), result.getEventType());
        assertEquals(eventDetails, result.getEventDetails());
        
        List<UserAudit> auditEvents = userAuditService.getAuditEvents(userId);
        
        assertNotNull(auditEvents);
        assertFalse(auditEvents.isEmpty());
        assertEquals(1, auditEvents.size());
        assertEquals(userId, auditEvents.get(0).getUserId());
    }
} 