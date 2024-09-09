package application.Services;

import application.Dto.UserDto;

import java.util.List;

public interface IUserService {


    public UserDto register(UserDto userDto);

    public UserDto Login(String username , String password);

    public UserDto update(Long id , UserDto userDto);

    public void delete(Long id);

    public UserDto getById(Long id);


    public List<UserDto> getAll();





}
