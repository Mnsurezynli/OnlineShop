package application.Services.impl;

import application.Dto.UserProfileDto;
import application.Repository.UserProfileRepository;
import application.Services.IUserProfileService;
import application.exception.ResourceNotFoundException;
import application.model.UserProfile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements IUserProfileService {


    private UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    @Override
    public UserProfileDto update(Long id, UserProfileDto userProfileDto) {
        Optional<UserProfile> userProfile = Optional.ofNullable(userProfileRepository.findById(userProfileDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found")));
        UserProfile userProfile1 = convertToEntity(userProfileDto);
        userProfileRepository.saveAndFlush(userProfile1);
        System.out.println("Profile information updated successfully");
        return userProfileDto;
    }

    @Override
    public UserProfileDto getById(Long id) {
        Optional<UserProfile> userProfile = Optional.ofNullable(userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found")));
            return convertToDto(userProfile.get());
    }

    @Override
    public List<UserProfileDto> getAll() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return
                userProfiles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserProfileDto convertToDto(UserProfile userProfile) {
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

    public UserProfile convertToEntity(UserProfileDto userProfileDto) {
        if (userProfileDto == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfile.getId());
        userProfile.setFirstName(userProfile.getFirstName());
        userProfile.setLastName(userProfile.getLastName());
        userProfile.setAddress(userProfile.getAddress());
        userProfile.setPhoneNumber(userProfile.getPhoneNumber());
        return userProfile;
    }
}
