package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class UserEntity {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phone;
    private String address;
    private ArrayList<BookEntity> books;
    private ArrayList<CourseEntity> courses;
    private ArrayList<UniversityEntity> universities;


    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserEntity(Long id, String name, String surname, String password, String email, String phone, String address, ArrayList<BookEntity> books, ArrayList<CourseEntity> courses, ArrayList<UniversityEntity> universities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.books = books;
        this.courses = courses;
        this.universities = universities;
    }
}
