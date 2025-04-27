package app.service.impl;

import app.dto.BookDto;
import app.exception.BookNotFoundException;
import app.model.BookEntity;
import app.repository.BookRepository;
import app.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void save(BookDto bookDto) {
        BookEntity bookEntity = new ModelMapper().map(bookDto, BookEntity.class);
        bookRepository.save(bookEntity);
    }

    @Override
    public ArrayList<BookDto> findAllByUserId(Long id) {
        ArrayList<BookEntity> books = bookRepository.findAllByUserId(id);
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        for (BookEntity bookEntity : books) {
            BookDto map = new ModelMapper().map(bookEntity, BookDto.class);
            bookDtos.add(map);
        }

        return bookDtos;
    }

    @Override
    public BookDto findById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id);
        return new ModelMapper().map(bookEntity, BookDto.class);
    }

    @Override
    @Async("taskExecutor")
    public BookDto delete(Long id) {
        BookEntity bookEntity = bookRepository.delete(id);
        return new ModelMapper().map(bookEntity, BookDto.class);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        if (bookDto.getId() == null) {
            throw new BookNotFoundException("id is null");
        }
        BookEntity bookEntity = new ModelMapper().map(bookDto, BookEntity.class);
        bookRepository.save(bookEntity);
        return new ModelMapper().map(bookEntity, BookDto.class);
    }

}
