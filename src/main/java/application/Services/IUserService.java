package application.Services;

import application.Dto.UserDto;
import application.Dto.UserProfileDto;

import java.util.List;

public interface IUserService {

    void register(UserDto userDto);

    void Login(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    void deleteById(Long id);

    UserDto getById(Long id);

    List<UserDto> getAll();
}
