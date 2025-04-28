package app.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public CourseEntity(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}
