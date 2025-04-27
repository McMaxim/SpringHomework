package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UniversityEntity {

    private Long id;
    private String name;

    public UniversityEntity() {
    }

    public UniversityEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
