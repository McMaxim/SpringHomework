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

    public BookEntity save(BookEntity bookEntity) {
        return new BookEntity();
    }

    public BookEntity delete(Long id) {
        return null;
    }

    public BookEntity findById(Long id) {
        return BookEntity.builder()
                .id(1l)
                .title("title1")
                .build();
    }
}
