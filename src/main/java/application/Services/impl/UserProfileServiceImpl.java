package application.Services.impl;

import application.Dto.UserProfileDto;
import application.Services.IUserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements IUserProfileService {
    @Override
    public UserProfileDto save(Long id, UserProfileDto userProfileDto) {
        return null;
    }

    @Override
    public UserProfileDto update(Long id, UserProfileDto userProfileDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserProfileDto getById(Long id) {
        return null;
    }
}
