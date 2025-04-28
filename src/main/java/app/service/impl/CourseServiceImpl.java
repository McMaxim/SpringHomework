package app.service.impl;


import app.dto.CourseDto;
import app.model.CourseEntity;
import app.repository.CourseRepository;
import app.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Caching(
            put = {@CachePut(value = "course", key = "#id")},
            evict = {@CacheEvict(value = "courses", allEntries = true)}
    )
    public Long save(CourseDto courseDto) {
        CourseEntity courseEntity = new ModelMapper().map(courseDto, CourseEntity.class);
        CourseEntity courseEntity1 = courseRepository.save(courseEntity);
        return courseEntity1.getId();
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
