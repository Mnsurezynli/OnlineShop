package application.controller;

import application.Dto.UserDto;
import application.Services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        iUserService.register(userDto);
        return new ResponseEntity<>("Registration was successfull", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        iUserService.Login(userDto);
        return new ResponseEntity<>("login was successfull", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto userDto1 = iUserService.update(id, userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        iUserService.deleteById(id);
        return new ResponseEntity<>("deleted successfully ", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto userDto = iUserService.getById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = iUserService.getAll();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
