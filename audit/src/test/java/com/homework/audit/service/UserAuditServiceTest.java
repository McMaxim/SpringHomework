package com.homework.audit.service;

import com.homework.audit.model.AuditEventType;
import com.homework.audit.model.UserAudit;
import com.homework.audit.repository.UserAuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuditServiceTest {

    @Mock
    private UserAuditRepository userAuditRepository;

    private UserAuditService userAuditService;

    @BeforeEach
    void setUp() {
        userAuditService = new UserAuditServiceImpl(userAuditRepository);
    }

    @Test
    void createAuditEvent_ValidData_ShouldCreateEvent() {
        UUID userId = UUID.randomUUID();
        AuditEventType eventType = AuditEventType.INSERT;
        String eventDetails = "User created a new record";
        
        UserAudit auditEvent = new UserAudit(userId, Instant.now(), eventType.name(), eventDetails);
        
        when(userAuditRepository.save(any(UserAudit.class))).thenReturn(auditEvent);
        
        UserAudit result = userAuditService.createAuditEvent(userId, eventType, eventDetails);
        
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(eventType.name(), result.getEventType());
        assertEquals(eventDetails, result.getEventDetails());
        
        verify(userAuditRepository, times(1)).save(any(UserAudit.class));
    }

    @Test
    void createAuditEvent_NullUserId_ShouldThrowException() {
        UUID userId = null;
        AuditEventType eventType = AuditEventType.SELECT;
        String eventDetails = "User viewed records";

        assertThrows(IllegalArgumentException.class, () -> 
            userAuditService.createAuditEvent(userId, eventType, eventDetails)
        );
        
        verify(userAuditRepository, never()).save(any(UserAudit.class));
    }

    @Test
    void getAuditEvents_ValidUserId_ShouldReturnEvents() {
        UUID userId = UUID.randomUUID();
        UserAudit auditEvent = new UserAudit(
            userId, 
            Instant.now(), 
            AuditEventType.UPDATE.name(), 
            "User updated a record"
        );
        
        when(userAuditRepository.findByUserIdOrderByEventTimeDesc(userId))
            .thenReturn(Collections.singletonList(auditEvent));
        
        List<UserAudit> result = userAuditService.getAuditEvents(userId);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(userId, result.get(0).getUserId());
        
        verify(userAuditRepository, times(1)).findByUserIdOrderByEventTimeDesc(userId);
    }

    @Test
    void getAuditEvents_NullUserId_ShouldThrowException() {
        UUID userId = null;

        assertThrows(IllegalArgumentException.class, () -> 
            userAuditService.getAuditEvents(userId)
        );
        
        verify(userAuditRepository, never()).findByUserIdOrderByEventTimeDesc(any(UUID.class));
    }
} 