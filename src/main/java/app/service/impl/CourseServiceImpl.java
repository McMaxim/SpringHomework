package app.service.impl;


import app.dto.BookDto;
import app.dto.CourseDto;
import app.model.BookEntity;
import app.model.CourseEntity;
import app.repository.CourseRepository;
import app.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Long save( CourseDto courseDto) {
        CourseEntity courseEntity = new ModelMapper().map(courseDto, CourseEntity.class);
        Long courseId = courseRepository.save(courseEntity);
        return courseId;
    }

    @Override
    public ArrayList<CourseDto> findAllByUserId(Long id) {
        ArrayList<CourseEntity> courses = courseRepository.findAllByUserId(id);
        ArrayList<CourseDto> courseDtos = new ArrayList<>();
        for (CourseEntity courseEntity : courses) {
            CourseDto map = new ModelMapper().map(courseEntity, CourseDto.class);
            courseDtos.add(map);
        }
        return courseDtos;
    }

    @Override
    public void delete(Long id) {

    }
}
