package com.homework.audit.service;

import com.homework.audit.model.AuditEventType;
import com.homework.audit.model.UserAudit;
import com.homework.audit.repository.UserAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserAuditServiceImpl implements UserAuditService {

    private final UserAuditRepository userAuditRepository;

    @Autowired
    public UserAuditServiceImpl(UserAuditRepository userAuditRepository) {
        this.userAuditRepository = userAuditRepository;
    }

    @Override
    public UserAudit createAuditEvent(UUID userId, AuditEventType eventType, String eventDetails) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        
        UserAudit auditEvent = new UserAudit(
                userId,
                Instant.now(),
                eventType.name(),
                eventDetails
        );
        
        return userAuditRepository.save(auditEvent);
    }

    @Override
    public List<UserAudit> getAuditEvents(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        return userAuditRepository.findByUserIdOrderByEventTimeDesc(userId);
    }
} 