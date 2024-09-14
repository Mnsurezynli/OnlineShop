package application.controller;

import application.Dto.UserProfileDto;
import application.Services.IUserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resources;
import java.util.List;

@RestController
@RequestMapping("/api/userProfile")
public class UserProfileController {

    private IUserProfileService iUserProfileService;

    public UserProfileController(IUserProfileService iUserProfileService) {
        this.iUserProfileService = iUserProfileService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserProfileDto> update(@PathVariable Long id, UserProfileDto userProfileDto) {
        UserProfileDto userProfileDto1 = iUserProfileService.update(id, userProfileDto);
        return new ResponseEntity<>(userProfileDto1, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getById(@PathVariable Long id) {
        UserProfileDto userProfileDto = iUserProfileService.getById(id);
        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAll() {
        List<UserProfileDto> userProfileDtos = iUserProfileService.getAll();
        return new ResponseEntity<>(userProfileDtos, HttpStatus.OK);
    }
}
