package app.service;

import app.model.Action;
import app.model.UserAudit;
import app.model.UserAuditKey;
import app.repository.UserAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAuditService {
    private final UserAuditRepository userAuditRepository;

    public UserAuditService(UserAuditRepository userAuditRepository) {
        this.userAuditRepository = userAuditRepository;
    }

    public void save(UserAudit userAudit) {
        userAuditRepository.save(userAudit);
    }

    public UserAudit findById(UserAuditKey userAuditKey) {
        return userAuditRepository.findById(userAuditKey).get();
    }

    List<UserAudit> findAll() {
        List<UserAudit> userAudits = userAuditRepository.findAll();
        return userAudits;
    }
}