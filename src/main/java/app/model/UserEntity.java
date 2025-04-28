package app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    @OneToMany
    private ArrayList<BookEntity> bookEntities;
    @OneToMany
    private ArrayList<CourseEntity> courseEntities;

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserEntity(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }
}
