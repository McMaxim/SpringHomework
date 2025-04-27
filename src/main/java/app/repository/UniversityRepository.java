package app.repository;

import app.model.UniversityEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UniversityRepository {

    private ArrayList<UniversityEntity> temp = new ArrayList<>();

    public ArrayList<UniversityEntity> findAllByUserId(Long id) {

        temp.add(new UniversityEntity(1L, "UniversityName1"));
        temp.add(new UniversityEntity(2L, "UniversityName2"));
        return temp;
    }

    public Long save(UniversityEntity universityEntity) {
        return 2L;
    }
}
