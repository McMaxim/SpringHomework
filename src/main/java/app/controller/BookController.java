package app.controller;

import app.model.BookEntity;
import app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<List<BookEntity>> findAllByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findAllByUserId(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<List<BookEntity>> addBookToUserById(@PathVariable Long id, @RequestBody BookEntity book) {
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}