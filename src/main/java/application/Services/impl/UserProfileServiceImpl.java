package application.Services.impl;

import application.Dto.UserProfileDto;
import application.Repository.UserProfileRepository;
import application.Services.IUserProfileService;
import application.exception.ResourceNotFoundException;
import application.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    @Override
    public UserProfileDto update(Long id, UserProfileDto userProfileDto) {
        UserProfile existingUserProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id " + id));
        existingUserProfile.setFirstName(userProfileDto.getFirstName());
        existingUserProfile.setLastName(userProfileDto.getLastName());
        existingUserProfile.setAddress(userProfileDto.getAddress());
        existingUserProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        userProfileRepository.saveAndFlush(existingUserProfile);

        System.out.println("Profile information updated successfully");
        return convertToDto(existingUserProfile);
    }

    @Override
    public UserProfileDto getById(Long id) {
        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id " + id));

        return convertToDto(userProfile);
    }

    @Override
    public List<UserProfileDto> getAll() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserProfileDto convertToDto(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(userProfile.getId());
        userProfileDto.setFirstName(userProfile.getFirstName());
        userProfileDto.setLastName(userProfile.getLastName());
        userProfileDto.setAddress(userProfile.getAddress());
        userProfileDto.setPhoneNumber(userProfile.getPhoneNumber());
        return userProfileDto;
    }

    private UserProfile convertToEntity(UserProfileDto userProfileDto) {
        if (userProfileDto == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileDto.getId());
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        userProfile.setAddress(userProfileDto.getAddress());
        userProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        return userProfile;
    }
}
