package app.controller;

import app.model.CourseEntity;
import app.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CourseEntity>> findAllByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findAllByUserId(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<List<CourseEntity>> save(@PathVariable Long userId, @RequestBody CourseEntity courseEntity) {
        courseService.save(userId, courseEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}