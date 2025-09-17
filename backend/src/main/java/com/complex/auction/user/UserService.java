package com.complex.auction.user;


import com.complex.auction.dto.RegistrationRequest;
import com.complex.auction.exceptions.EmailAlreadyFoundException;
import com.complex.auction.exceptions.UsernameAlreadyFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

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
                passwordEncoder.encode(registrationRequest.password())
        );

        return userRepository.save(registerUser);
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


    public User updateUsername(String oldUsername, String newUsername) {
        Optional<User> userWithUsernameExists = userRepository.findUserByUsername(newUsername);

        if (userWithUsernameExists.isPresent())
            throw new UsernameAlreadyFoundException("Another user with this username exists");

        Optional<User> currentUser = findUser(oldUsername);

        if (currentUser.isEmpty())
            throw new UsernameNotFoundException("User not found: " + currentUser);

        User userToUpdate = currentUser.get();
        userToUpdate.setUsername(newUsername);

        userToUpdate.setLastUpdated(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }

    public User updatePassword(String username, String newPassword) {
        Optional<User> currentUser = userRepository.findUserByUsername(username);

        if (currentUser.isEmpty())
            throw new UsernameNotFoundException("User not found: " + username);


        User userToUpdate = currentUser.get();
        String encryptedPassword = passwordEncoder.encode(newPassword);
        userToUpdate.setPassword(encryptedPassword);

        userToUpdate.setLastUpdated(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }
}
