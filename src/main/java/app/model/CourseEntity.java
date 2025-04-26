package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CourseEntity {
    private Long id;
    private String title;

    public CourseEntity() {
    }

    public CourseEntity(Long id, String title) {
        this.id = id;

        this.title = title;
    }

}
