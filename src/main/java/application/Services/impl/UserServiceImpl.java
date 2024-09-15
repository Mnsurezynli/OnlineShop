package application.Services.impl;

import application.Dto.UserDto;
import application.Dto.UserProfileDto;
import application.Repository.UserProfileRepository;
import application.Repository.UserRepository;
import application.Services.IUserService;
import application.exception.*;
import application.model.User;
import application.model.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {


    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Validated
    @Transactional
    @Override
    public void register(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
                if(user.isPresent()){
               throw  new ResourceAlreadyExistsException("User Already exists");
                }
            User user1= convertToEntity(userDto);
        userRepository.saveAndFlush(user1);
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
        Optional<User> user = Optional.ofNullable(userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id)));
        User user1 = convertToEntity(userDto);
        userRepository.saveAndFlush(user1);
        System.out.println("Profile information updated successfully");
        return userDto;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        userRepository.deleteById(id);
        System.out.println("User deleted successfully");
    }


    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + id));

        return convertToDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return
                users.stream().map(this::convertToDto).collect(Collectors.toList());


    }


    public UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }
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
            userDto.setUserProfile(userProfileDto);


        }
        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user1 = new User();
        user1.setId(userDto.getId());
        user1.setUsername(userDto.getUsername());
        user1.setPassword(userDto.getPassword());
        user1.setEmail(userDto.getEmail());

        if (userDto.getUserProfile() != null) {
            UserProfile userProfile = new UserProfile();
            userProfile.setId(userDto.getUserProfile().getId());
            userProfile.setFirstName(userDto.getUserProfile().getFirstName());
            userProfile.setLastName(userDto.getUserProfile().getLastName());
            userProfile.setAddress(userDto.getUserProfile().getAddress());
            userProfile.setPhoneNumber(userDto.getUserProfile().getPhoneNumber());
            user1.setUserProfile(userProfile);
            System.out.println("UserProfile set to User: " + userProfile);
        }
            return user1;
        }

}