package app.service;

;
import app.model.Action;
import app.model.UserAudit;
import app.model.UserAuditKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Testcontainers
class UserAuditServiceTest {

    @Container
    private static final CassandraContainer<?> cassandraContainer =
            new CassandraContainer<>(DockerImageName.parse("cassandra:4.1"))
                    .withExposedPorts(9042)
                    .withStartupTimeout(Duration.ofSeconds(60));
    @Autowired
    private UserAuditService userAuditService;

    @BeforeAll
    static void setUp() {
        System.setProperty("9042", String.valueOf(cassandraContainer.getMappedPort(9042)));
        cassandraContainer.start();
    }

    @Test
    void SuccessfullySaveUserAudit() {
        UserAuditKey userAuditKey = new UserAuditKey(UUID.randomUUID(), LocalDateTime.now());

        String eventDetails = "test event";
        UserAudit userAudit = new UserAudit(userAuditKey, Action.INSERT, eventDetails);
        userAuditService.save(userAudit);
        List<UserAudit> users = userAuditService.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    void SuccessfullyFindByIdUserAudit() {
        UserAuditKey userAuditKey = new UserAuditKey(UUID.randomUUID(), LocalDateTime.now());
        String eventDetails = "test event";
        UserAudit userAudit = new UserAudit(userAuditKey, Action.INSERT, eventDetails);
        userAuditService.save(userAudit);
        UserAudit userAudit1 = userAuditService.findById(userAuditKey);
        assertEquals(eventDetails, userAudit1.getEventDetails());
    }

    @Test
    void SuccessfullyFindAllUsers() {
        UserAuditKey userAuditKey = new UserAuditKey(UUID.randomUUID(), LocalDateTime.now());
        String eventDetails = "test event";
        UserAudit userAudit = new UserAudit(userAuditKey, Action.INSERT, eventDetails);
        userAuditService.save(userAudit);
        List<UserAudit> users = userAuditService.findAll();
        assertFalse(users.isEmpty());
    }

    @Test
    void FailToSaveUserAudit() {
        UserAuditKey userAuditKey = new UserAuditKey(UUID.randomUUID(), LocalDateTime.now());
        String eventDetails = "test event";
        UserAudit userAudit = new UserAudit(userAuditKey, Action.INSERT, eventDetails);
        userAuditService.save(userAudit);
        userAuditService.save(userAudit);
        List<UserAudit> users = userAuditService.findAll();
        assertNotEquals(1, users.size());
    }


    @Test
    void FailToFindAllUsers() {
        UserAuditKey userAuditKey = new UserAuditKey(UUID.randomUUID(), LocalDateTime.now());
        String eventDetails = "test event";
        UserAudit userAudit = new UserAudit(userAuditKey, Action.INSERT, eventDetails);
        userAuditService.save(userAudit);
        userAuditService.save(userAudit);
        userAuditService.save(userAudit);
        assertNotEquals(1, userAuditService.findAll().size());
    }
}