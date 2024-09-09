package application.Services.impl;

import application.Dto.UserDto;
import application.Services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {


    @Override
    public UserDto register(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto Login(String username, String password) {
        return null;
    }

    @Override
    public UserDto update(Long id , UserDto userDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDto getById(Long id) {
        return null;
    }

    @Override
    public List<UserDto> getAll() {
        return null;
    }
}
