package app.controller.impl;

import app.controller.UniversityController;
import app.dto.UniversityDto;
import app.model.UniversityEntity;
import app.service.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UniversityControllerImpl implements UniversityController {
    private final UniversityService universityService;

    public UniversityControllerImpl(UniversityService universityService) {
        this.universityService = universityService;
    }

    public ResponseEntity<List<UniversityDto>> findAllByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(universityService.findAllByUserId(id));
    }

    public ResponseEntity<List<UniversityDto>> addUniversityByUserId(@PathVariable Long userId, @RequestBody UniversityDto universityDto) {
        universityService.addUniversityByUserId(universityDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        universityService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}