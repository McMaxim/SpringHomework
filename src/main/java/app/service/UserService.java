package app.service;

import app.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    public Long save(UserEntity userEntity);

    public UserEntity findById(Long id);
}
