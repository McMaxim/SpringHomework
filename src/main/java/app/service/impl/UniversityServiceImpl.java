package app.service.impl;

import app.dto.UniversityDto;
import app.model.UniversityEntity;
import app.repository.UniversityRepository;
import app.service.UniversityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UniversityServiceImpl implements UniversityService {


    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public Long addUniversityByUserId(UniversityDto universityDto) {
        UniversityEntity universityEntity = new ModelMapper().map(universityDto, UniversityEntity.class);
        Long universityId = universityRepository.save(universityEntity);
        return universityId;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ArrayList<UniversityDto> findAllByUserId(Long id) {
        ArrayList<UniversityEntity> universities = universityRepository.findAllByUserId(id);
        ArrayList<UniversityDto> universityDtos = new ArrayList<>();
        for (UniversityEntity universityEntity : universities) {
            UniversityDto universityDto = new ModelMapper().map(universityEntity, UniversityDto.class);
            universityDtos.add(universityDto);
        }
        return universityDtos;
    }
}
