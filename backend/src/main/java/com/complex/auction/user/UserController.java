package com.complex.auction.user;

import com.complex.auction.dto.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/v1/signup", consumes = "application/json")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping(value = "/v1/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Login successful");
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

    @PatchMapping(value = "/v1/update/username")
    public ResponseEntity<User> updateUsername(@RequestBody String oldUsername, @RequestBody String newUsername) {
        User updatedUser = userService.updateUsername(oldUsername, newUsername);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(value = "/v1/update/pasword")
    public ResponseEntity<User> updatePassword(@RequestBody String username, @RequestBody String newPassword) {
        User updatedUser = userService.updatePassword(username, newPassword);

        return ResponseEntity.ok(updatedUser);

    }


}
