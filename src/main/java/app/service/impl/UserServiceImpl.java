package app.service.impl;

import app.dto.UserDto;
import app.model.UserEntity;
import app.repository.UserRepository;
import app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long save(UserDto userDto) {
        UserEntity userEntity = new ModelMapper().map(userDto, UserEntity.class);
        Long userId = userRepository.save(userEntity);
        return userId;
    }

    @Override
    public UserDto findById(Long id) {
        UserEntity user = userRepository.findById(id);
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public void delete(Long id) {

    }
}
