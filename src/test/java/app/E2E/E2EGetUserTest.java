package app.E2E;

import app.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class E2EGetUserTest {
  private static final UserEntity testUserResponse =
          new UserEntity(1L,"Максим");

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetUser() {
    String url = "http://localhost:" + port + "/api/users/1";
    ResponseEntity<UserEntity> response = restTemplate.getForEntity(url, UserEntity.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(testUserResponse.getName(), response.getBody().getName());
  }
}
