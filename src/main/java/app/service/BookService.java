package app.service;

import app.model.BookEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BookService {


    public void save(BookEntity bookEntity);

    public ArrayList<BookEntity> findAllByUserId(Long id);
}
