package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Builder
@Getter
@Setter
public class BookEntity {

    private Long id;
    private String title;
    private String author;
    private String publisher;


    public BookEntity() {
    }

    public BookEntity(Long id, String title, String author, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(publisher, that.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher);
    }
}

