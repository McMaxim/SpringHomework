package app.repository;

import app.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {


    public UserEntity findById(Long id) {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName("Максим");
        return userEntity;
    }

    public Long save(UserEntity user) {
        return 3L;
    }
}
