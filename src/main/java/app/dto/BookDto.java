package app.dto;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(publisher, bookDto.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher);
    }
}
