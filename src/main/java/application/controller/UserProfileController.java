package application.controller;

import application.Dto.UserProfileDto;
import application.Services.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userprofiles")
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
    public ResponseEntity<UserProfileDto> getById(@PathVariable Long id) {
        UserProfileDto userProfileDto = userProfileService.getById(id);
        return ResponseEntity.ok(userProfileDto);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAll() {
        List<UserProfileDto> userProfiles = userProfileService.getAll();
        return ResponseEntity.ok(userProfiles);
    }
}
