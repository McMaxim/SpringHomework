package app.controller;

import app.dto.UniversityDto;
import app.model.UniversityEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/university")
@Tag(name = "University API", description = "Управление Университетом")
public interface UniversityController {

    @Operation(summary = "получение университетов по идентификатора пользователя")
    @ApiResponse(responseCode = "200", description = "университеты найдены")
    @GetMapping("/user/{id}")
    ResponseEntity<List<UniversityDto>> findAllByUserId(@PathVariable Long id);

    @Operation(summary = " добавление университета по идентификатора пользователя")
    @ApiResponse(responseCode = "200", description = "университеты найдены")
    @PutMapping("/user/{userId}")
    ResponseEntity<List<UniversityDto>> addUniversityByUserId(@PathVariable Long userId, @RequestBody UniversityDto universityDto);

    @Operation(summary = "удаление  университета")
    @ApiResponse(responseCode = "200", description = "университет удалён")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long id);
}
