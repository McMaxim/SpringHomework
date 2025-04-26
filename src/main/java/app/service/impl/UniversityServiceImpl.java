package app.service.impl;

import app.model.UniversityEntity;
import app.repository.UniversityRepository;
import app.service.UniversityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UniversityServiceImpl implements UniversityService {


    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public Long addUniversityByUserId(UniversityEntity universityEntity) {
        Long universityId = universityRepository.save(universityEntity);
        return universityId;
    }

    public ArrayList<UniversityEntity> findAllByUserId(Long id) {
        ArrayList<UniversityEntity> universities = universityRepository.findAllByUserId(id);
        return universities;
    }
}
