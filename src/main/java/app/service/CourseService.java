package app.service;

import app.dto.CourseDto;
import app.model.CourseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {

    public Long save( CourseDto courseDto);

    public ArrayList<CourseDto> findAllByUserId(Long id);

    void delete(Long id);
}
