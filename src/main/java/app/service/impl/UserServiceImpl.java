package app.service.impl;

import app.dto.UserDto;
import app.model.UserEntity;
import app.repository.UserRepository;
import app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, timeout = 1000, readOnly = false,propagation = Propagation.REQUIRED)
    @Override
    public Long save(UserDto userDto) {
        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);
        UserEntity userEntity1 = userRepository.save(userEntity);
        return userEntity1.getId();
    }

    @Override
    public UserDto findById(Long id) {
        UserEntity user = userRepository.findById(id).get();
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public void delete(Long id) {

    }
}
