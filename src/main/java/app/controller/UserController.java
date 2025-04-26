package app.controller;

import app.dto.UserDto;
import app.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public interface UserController {

    @Operation(summary = "сохранение пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь сахранон")
    @PostMapping()
    ResponseEntity<Long> save(@RequestBody UserDto userDto);

    @Operation(summary = "Получить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable Long id);

    @Operation(summary = "удаление  пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удалён")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long id);
}
