package app.service;

import app.model.UniversityEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UniversityService {

     ArrayList<UniversityEntity> findAllByUserId(Long id);


     Long addUniversityByUserId(UniversityEntity universityEntity);
}
