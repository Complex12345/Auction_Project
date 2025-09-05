package com.complex.auction.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/v1/signup", consumes = "application/json")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping(value = "/v1/findEmail")
    public ResponseEntity<Boolean> checkIfEmailExists(@RequestBody String email) {
        boolean foundEmail = userService.findEmailExists(email);
        if (foundEmail)
            return ResponseEntity.status(HttpStatus.FOUND).body(true);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
    }

    @GetMapping(value = "/v1/findUsername")
    public ResponseEntity<Boolean> checkIfUsernameExists(@RequestBody String Username) {
        boolean foundUsername = userService.findUsernameExists(Username);
        if (foundUsername)
            return ResponseEntity.status(HttpStatus.FOUND).body(true);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
    }


}
