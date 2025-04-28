package app.dto;

import app.model.BookEntity;
import app.model.CourseEntity;
import app.model.UniversityEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Данные пользователя")
public class UserDto {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phone;
    private String address;
    private ArrayList<BookDto> books;
    private ArrayList<CourseDto> courses;
    private ArrayList<UniversityDto> universities;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(surname, userDto.surname) && Objects.equals(password, userDto.password) && Objects.equals(email, userDto.email) && Objects.equals(phone, userDto.phone) && Objects.equals(address, userDto.address) && Objects.equals(books, userDto.books) && Objects.equals(courses, userDto.courses) && Objects.equals(universities, userDto.universities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, password, email, phone, address, books, courses, universities);
    }
}
