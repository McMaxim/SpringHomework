package app.service.impl;


import app.model.CourseEntity;
import app.repository.CourseRepository;
import app.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Long save(long userId, CourseEntity courseEntity) {
        Long courseId = courseRepository.save(courseEntity);
        return courseId;
    }

    @Override
    public ArrayList<CourseEntity> findAllByUserId(Long id) {
        ArrayList<CourseEntity> courses = courseRepository.findAllByUserId(id);
        return courses;
    }
}
