package application.Services;

import application.Dto.UserProfileDto;

public interface IUserProfileService {

    public UserProfileDto save(Long id ,UserProfileDto userProfileDto);

    public  UserProfileDto update(Long id ,UserProfileDto userProfileDto);

    public void  delete(Long id);

    public UserProfileDto getById(Long id);



}
