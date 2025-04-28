package app.repository;

import app.model.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
}
