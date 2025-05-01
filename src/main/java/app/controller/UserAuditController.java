package app.controller;


import app.dto.UserAuditDto;
import app.model.UserAuditKey;
import app.service.UserAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/audit")
public class UserAuditController {
    private static final Logger LOG = LoggerFactory.getLogger(UserAuditController.class);
    private final UserAuditService userAuditService;

    public UserAuditController(UserAuditService userAuditService) {
        this.userAuditService = userAuditService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuditDto> findById(
            @PathVariable UUID id,
            @RequestParam("eventTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventTime) {

        UserAuditDto userAuditInfos = userAuditService.findById(new UserAuditKey(id, eventTime));
        LOG.debug("AuditInfo is got");
        return ResponseEntity.ok(userAuditInfos);
    }

}