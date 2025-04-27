package app.service;

import app.dto.UserDto;
import app.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    public Long save(UserDto userDto);

    public UserDto findById(Long id);

    void delete(Long id);
}
