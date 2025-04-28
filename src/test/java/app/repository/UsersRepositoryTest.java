package app.repository;

import app.model.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional(value = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class UsersRepositoryTest {

  @Container @ServiceConnection
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");

  @Autowired UserRepository userRepository;

  @Test
  void findByEmailEquals()  {
    UserEntity testUser = userRepository.save(new UserEntity("test","test", "test", "test@test.com"));
    UserEntity responseUser = userRepository.findByEmail("test@test.com").orElseThrow();

    assertEquals(testUser.getEmail(), responseUser.getEmail());
  }


  @Test
  void findAll() {
    UserEntity testUser1 = userRepository.save(new UserEntity("test","test", "test", "test@test.com"));
    UserEntity testUser2 = userRepository.save(new UserEntity("test","test", "test", "test@test.com"));
    List<UserEntity> testUserList = new ArrayList<>();
    testUserList.add(testUser1);
    testUserList.add(testUser2);

    List<UserEntity> responseUserList = userRepository.findAll();

    assertEquals(testUserList, responseUserList);
  }

  @Test
  void findById() {
    UserEntity testUser = userRepository.save(new UserEntity("test","test", "test", "test@test.com"));

    UserEntity responseUser = userRepository.findById(testUser.getId()).orElseThrow();

    assertEquals(testUser, responseUser);
  }
}