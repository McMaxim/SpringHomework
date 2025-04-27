package app.service;

import app.dto.UniversityDto;
import app.model.UniversityEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface UniversityService {

     ArrayList<UniversityDto> findAllByUserId(Long id);


     Long addUniversityByUserId(UniversityDto universityDto);

     void delete(Long id);
}
