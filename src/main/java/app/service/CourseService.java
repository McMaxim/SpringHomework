package app.service;

import app.dto.CourseDto;
import org.springframework.stereotype.Service;


@Service
public interface CourseService {

    Long save(CourseDto courseDto);

    void delete(Long id);
}
