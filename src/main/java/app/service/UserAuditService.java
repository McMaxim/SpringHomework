package app.service;

import app.dto.UserAuditDto;
import app.exception.UserNotFoundException;
import app.model.Outbox;
import app.model.UserAudit;
import app.model.UserAuditKey;
import app.repository.OutboxRepository;
import app.repository.UserAuditRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAuditService {
    private final UserAuditRepository userAuditRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxRepository outboxRepository;


    public UserAuditService(UserAuditRepository userAuditRepository, KafkaTemplate<String, String> kafkaTemplate, OutboxRepository outboxRepository) {
        this.userAuditRepository = userAuditRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.outboxRepository = outboxRepository;
    }

    public void save(UserAudit userAudit) {
        userAuditRepository.save(userAudit);
        outboxRepository.save(Outbox.builder()
                        .id(UUID.randomUUID())
                        .data(userAudit.toString())
                .build());
    }

    public UserAuditDto findById(UserAuditKey userAuditKey) {
        UserAudit userAudit = userAuditRepository.findById(userAuditKey).orElseThrow(() ->new UserNotFoundException(userAuditKey.toString()));
        UserAuditDto userAuditDto = UserAuditDto.builder()
                .key(userAudit.getKey())
                .eventType(userAudit.getEventType())
                .eventDetails(userAudit.getEventDetails())
                .build();
        kafkaTemplate.send("topic1", userAuditDto.toString());
        return userAuditDto;
    }

    List<UserAudit> findAll() {
        List<UserAudit> userAudits = userAuditRepository.findAll();
        return userAudits;
    }

    @KafkaListener(topics = "topic1", groupId = "my-group")
    public void consume(String message) {
        System.out.println("Получено сообщение: " + message);

    }
}