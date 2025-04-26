package app.controller;

import app.model.UniversityEntity;
import app.service.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/university")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UniversityEntity>> findAllByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(universityService.findAllByUserId(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<List<UniversityEntity>> addUniversityByUserId(@PathVariable Long usarId, @RequestBody UniversityEntity universityEntity) {
        universityService.addUniversityByUserId(universityEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}