package app.repository;

import app.model.CourseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CourseRepository {

    private ArrayList<CourseEntity> temp = new ArrayList<>();


    public ArrayList<CourseEntity> findAllByUserId(Long id) {
        temp.add(new CourseEntity(1L, "Java course"));
        temp.add(new CourseEntity(2L, "Spring course"));
        return temp;
    }

    public Long save(CourseEntity courseEntity) {
        return 3L;
    }
}
