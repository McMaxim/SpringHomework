package app.repository;

import app.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


    ArrayList<BookEntity> findAllByUserEntity_Id(Long id);
}
