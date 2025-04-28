package app.repository;

import app.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
}
