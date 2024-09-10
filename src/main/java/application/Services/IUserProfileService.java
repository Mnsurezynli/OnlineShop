package application.Services;

import application.Dto.UserDto;
import application.Dto.UserProfileDto;

import java.util.List;

public interface IUserProfileService {


     UserProfileDto update(Long id ,UserProfileDto userProfileDto);

     UserProfileDto getById(Long id);

    List<UserProfileDto> getAll();

}
