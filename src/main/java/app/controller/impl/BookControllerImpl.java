package app.controller.impl;

import app.controller.BookController;
import app.dto.BookDto;
import app.model.BookEntity;
import app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }


    public ResponseEntity<List<BookDto>> findAllByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findAllByUserId(id));
    }

    public ResponseEntity<List<BookDto>> addBookToUserById(@PathVariable Long id, @RequestBody BookDto book) {
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<BookDto> findById(Long id) {
        BookDto bookDto = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @Override
    public ResponseEntity<BookDto> update(BookDto book) {
        BookDto bookDto = bookService.update(book);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }
}