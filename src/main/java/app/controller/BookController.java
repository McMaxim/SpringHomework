package app.controller;

import app.dto.BookDto;
import app.model.BookEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/books")
@Tag(name = "Book API", description = "Управление Книгам")
public interface BookController {

    @Operation(summary = " получение книгов по идентификатора пользователя")
    @ApiResponse(responseCode = "200", description = "список книг")
    @GetMapping("/users/{id}")
    ResponseEntity<List<BookDto>> findAllByUserId(@PathVariable Long id);

    @Operation(summary = "добавление книг пользователю")
    @ApiResponse(responseCode = "200", description = "пользовател удалён")
    @PutMapping("/users/{id}")
    ResponseEntity<List<BookDto>> addBookToUserById(@PathVariable Long id, @RequestBody BookDto book);

    @Operation(summary = "удаление  книг")
    @ApiResponse(responseCode = "200", description = "книга удалён")
    @DeleteMapping("/{id}")
    ResponseEntity<BookDto> delete(@PathVariable Long id);


    @Operation(summary = "найти книгу по id")
    @ApiResponse(responseCode = "200", description = "книга найден")
    @GetMapping("/{id}")
    ResponseEntity<BookDto> findById(@PathVariable Long id);

    @Operation(summary = "найти книгу по id")
    @ApiResponse(responseCode = "200", description = "книга найден")
    @PutMapping("/{id}")
    ResponseEntity<BookDto> update(@RequestBody BookDto book);


}
