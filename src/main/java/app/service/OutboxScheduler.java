package app.service;

import app.model.Outbox;
import app.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OutboxScheduler {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final OutboxRepository outboxRepository;

    public OutboxScheduler(
            KafkaTemplate<String, String> kafkaTemplate,
            OutboxRepository outboxRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void processOutbox() {
        List<Outbox> result = outboxRepository.findAll();
        for (Outbox outboxRecord : result) {
            CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send("my_topic", outboxRecord.getData());
        }
        outboxRepository.deleteAll(result);
    }

}