package app.repository;

import app.model.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class BookRepository {

    private ArrayList<BookEntity> temp = new ArrayList<>();

    public ArrayList<BookEntity> findAllByUserId(Long id) {

        temp.add(new BookEntity(1L, "title1", "Максим", "test"));
        temp.add(new BookEntity(2L, "title2", "Максим", "test"));
        return temp;
    }

    public Long save(BookEntity bookEntity) {
        return 3L;
    }
}
