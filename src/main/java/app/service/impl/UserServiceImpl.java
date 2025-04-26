package app.service.impl;

import app.model.UserEntity;
import app.repository.UserRepository;
import app.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long save(UserEntity userEntity) {
        Long userId = userRepository.save(userEntity);
        return userId;
    }

    @Override
    public UserEntity findById(Long id) {
        UserEntity user = userRepository.findById(id);
        return user;
    }
}
