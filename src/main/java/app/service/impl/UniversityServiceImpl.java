package app.service.impl;

import app.repository.UniversityRepository;
import app.service.UniversityService;
import org.springframework.stereotype.Service;
@Service
public class UniversityServiceImpl implements UniversityService {


    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public void delete(Long id) {

    }
}
