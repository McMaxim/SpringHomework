package app.service;

import app.dto.BookDto;
import app.model.BookEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BookService {


    public void save(BookDto bookDto);

    public ArrayList<BookDto> findAllByUserId(Long id);

    public BookDto findById(Long id);

    BookDto delete(Long id);

    BookDto update(BookDto bookDto);
}
