package com.complex.auction.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.complex.auction.dto.RegistrationRequest;
import com.complex.auction.exceptions.EmailAlreadyFoundException;
import com.complex.auction.exceptions.UsernameAlreadyFoundException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(RegistrationRequest registrationRequest) {

        if (findUsernameExists(registrationRequest.username()))
            throw new UsernameAlreadyFoundException(registrationRequest.username() + " is taken");
        if (findEmailExists(registrationRequest.email()))
            throw new EmailAlreadyFoundException(registrationRequest.email() + " is taken");

        User registerUser = new User(
                registrationRequest.email(),
                registrationRequest.username(),
                passwordEncoder.encode(registrationRequest.password()));

        return userRepository.save(registerUser);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean findEmailExists(String email) {
        Optional<User> foundUser = userRepository.findUserByEmail(email);
        return foundUser.isPresent();
    }

    public boolean findUsernameExists(String username) {
        Optional<User> foundUser = userRepository.findUserByUsername(username);
        return foundUser.isPresent();
    }

    public Optional<User> findUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User updateUsername(UUID userId, String newUsername) {

        if (userRepository.existsByUsername(newUsername)) {
            throw new UsernameAlreadyFoundException(
                    "Another user with this username already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setUsername(newUsername);
        user.setLastUpdated(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User updatePassword(UUID userId, String newPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastUpdated(LocalDateTime.now());

        return userRepository.save(user);
    }
}
