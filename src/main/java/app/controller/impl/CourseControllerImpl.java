package app.controller.impl;

import app.controller.CourseController;
import app.dto.CourseDto;
import app.model.CourseEntity;
import app.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseControllerImpl implements CourseController {
    private final CourseService courseService;

    public CourseControllerImpl(CourseService courseService) {
        this.courseService = courseService;
    }

    public ResponseEntity<List<CourseDto>> save( @RequestBody CourseDto courseDto) {
        courseService.save(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}