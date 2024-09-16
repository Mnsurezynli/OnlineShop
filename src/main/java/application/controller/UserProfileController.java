package application.controller;

import application.Dto.UserProfileDto;
import application.Services.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userprofiles")
public class UserProfileController {

    private final IUserProfileService userProfileService;

    @Autowired
    public UserProfileController(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PutMapping("/{id}")
    public UserProfileDto update(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        return userProfileService.update(id, userProfileDto);
    }

    @GetMapping("/{id}")
    public UserProfileDto getById(@PathVariable Long id) {
        return userProfileService.getById(id);
    }

    @GetMapping
    public List<UserProfileDto> getAll() {
        return userProfileService.getAll();
    }
}
