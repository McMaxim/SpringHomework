package com.homework.audit.service;

import com.homework.audit.model.AuditEventType;
import com.homework.audit.model.UserAudit;

import java.util.List;
import java.util.UUID;

public interface UserAuditService {
    
    UserAudit createAuditEvent(UUID userId, AuditEventType eventType, String eventDetails);
    
    List<UserAudit> getAuditEvents(UUID userId);
} 