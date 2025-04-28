package app.service;

import app.dto.UniversityDto;
import org.springframework.stereotype.Service;


@Service
public interface UniversityService {
     void delete(Long id);
}
