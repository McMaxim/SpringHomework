package app.service.impl;

import app.model.BookEntity;
import app.repository.BookRepository;
import app.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(BookEntity bookEntity) {
        Long bookId = bookRepository.save(bookEntity);
    }

    @Override
    public ArrayList<BookEntity> findAllByUserId(Long id) {
        ArrayList<BookEntity> books = bookRepository.findAllByUserId(id);
        return books;
    }
}
