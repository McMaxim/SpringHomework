package app.controller;

import app.dto.CourseDto;
import app.model.CourseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/courses")
@Tag(name = "Course API", description = "Управление Курсом")
public interface CourseController {

    @Operation(summary = "сохранение  Курсоа")
    @ApiResponse(responseCode = "200", description = "Курс сохранон")
    @PostMapping("")
    ResponseEntity<List<CourseDto>> save(@RequestBody CourseDto courseDto);

    @Operation(summary = "удаление  Курса")
    @ApiResponse(responseCode = "200", description = "Курс удалён")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long id);
}
