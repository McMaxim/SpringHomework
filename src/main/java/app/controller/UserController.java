package app.controller;

import app.model.UserEntity;
import app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Long> save(@RequestBody UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        UserEntity userEntity = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }
}