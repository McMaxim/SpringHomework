package app.dto;

import app.model.BookEntity;
import app.model.CourseEntity;
import app.model.UniversityEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class UserDto {

    private Long id;
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;
    private String surname;
    private String password;
    @Email(message = "Некорректный формат email")
    private String email;
    private String phone;
    private String address;
    private ArrayList<BookDto> books;
    private ArrayList<CourseDto> courses;
    private ArrayList<UniversityDto> universities;
}
