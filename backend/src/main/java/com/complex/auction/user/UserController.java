package com.complex.auction.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complex.auction.dto.LoginRequest;
import com.complex.auction.dto.RegistrationRequest;
import com.complex.auction.dto.UpdatePasswordRequest;
import com.complex.auction.dto.UpdateUsernameRequest;
import com.complex.auction.security.AuthUtil;
import com.complex.auction.security.JwtUtil;
import java.util.UUID;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/v1/signup", consumes = "application/json")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User createdUser = userService.saveUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping(value = "/v1/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findUserByEmail(loginRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtUtil.generateToken(authentication, user.getId());
        return ResponseEntity.ok(jwt);
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

    @PatchMapping("/v1/update/username")
    public ResponseEntity<User> updateUsername(
            @RequestBody UpdateUsernameRequest request,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);

        User updatedUser = userService.updateUsername(
                userId,
                request.newUsername());

        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/v1/update/password")
    public ResponseEntity<User> updatePassword(
            @RequestBody UpdatePasswordRequest request,
            Authentication authentication) {

        UUID userId = AuthUtil.extractUUID(authentication);

        User updatedUser = userService.updatePassword(
                userId,
                request.newPassword());

        return ResponseEntity.ok(updatedUser);
    }

}
