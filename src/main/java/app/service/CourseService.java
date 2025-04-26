package app.service;

import app.model.CourseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface CourseService {

    public Long save(long userId, CourseEntity courseEntity);

    public ArrayList<CourseEntity> findAllByUserId(Long id);

}
