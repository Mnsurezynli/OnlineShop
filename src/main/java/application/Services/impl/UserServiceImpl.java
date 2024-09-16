package application.Services.impl;

import application.Dto.UserDto;
import application.Dto.UserProfileDto;
import application.Repository.UserProfileRepository;
import application.Repository.UserRepository;
import application.Services.IUserService;
import application.exception.*;
import application.model.User;
import application.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements IUserService {

    private  UserRepository userRepository;
    private  UserProfileRepository userProfileRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Validated
    @Transactional
    @Override
    public void register(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User Already exists");
        }
        User user = convertToEntity(userDto);
        if (user.getUserProfile() != null) {
            userProfileRepository.save(user.getUserProfile());
        }

        userRepository.saveAndFlush(user);
        System.out.println("Registration was successful ");
    }

    @Validated
    @Override
    public void Login(UserDto userDto) {
        Optional<User> user = userRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        if (user.isPresent()) {
            System.out.println("Login was successful");
        } else {
            throw new InvalidInputException("Invalid username or password");
        }
    }

    @Validated
    @Transactional
    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (userDto.getUserProfileDto() != null) {
            UserProfile userProfile = user.getUserProfile();
            if (userProfile == null) {
                userProfile = new UserProfile();
                user.setUserProfile(userProfile);
            }

            userProfile.setFirstName(userDto.getUserProfileDto().getFirstName());
            userProfile.setLastName(userDto.getUserProfileDto().getLastName());
            userProfile.setAddress(userDto.getUserProfileDto().getAddress());
            userProfile.setPhoneNumber(userDto.getUserProfileDto().getPhoneNumber());

            userProfileRepository.save(userProfile);
        }

        // Update User details
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        userRepository.saveAndFlush(user);
        System.out.println("Profile information updated successfully");
        return convertToDto(user);
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (user.getUserProfile() != null) {
            userProfileRepository.delete(user.getUserProfile());
        }
        userRepository.deleteById(id);
        System.out.println("User deleted successfully");
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        return convertToDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());

        if (user.getUserProfile() != null) {
            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setId(user.getUserProfile().getId());
            userProfileDto.setFirstName(user.getUserProfile().getFirstName());
            userProfileDto.setLastName(user.getUserProfile().getLastName());
            userProfileDto.setAddress(user.getUserProfile().getAddress());
            userProfileDto.setPhoneNumber(user.getUserProfile().getPhoneNumber());
            userDto.setUserProfileDto(userProfileDto);
        }

        return userDto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        if (userDto.getUserProfileDto() != null) {
            UserProfile userProfile = new UserProfile();
            userProfile.setId(userDto.getUserProfileDto().getId());
            userProfile.setFirstName(userDto.getUserProfileDto().getFirstName());
            userProfile.setLastName(userDto.getUserProfileDto().getLastName());
            userProfile.setAddress(userDto.getUserProfileDto().getAddress());
            userProfile.setPhoneNumber(userDto.getUserProfileDto().getPhoneNumber());
            user.setUserProfile(userProfile);
        }

        return user;
    }

}