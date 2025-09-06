package com.complex.auction.user;


import com.complex.auction.exceptions.EmailAlreadyFoundException;
import com.complex.auction.exceptions.UsernameAlreadyFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {

        if (findUsernameExists(user.getUsername()))
            throw new UsernameAlreadyFoundException(user.getUsername() + " is taken");
        if(findEmailExists(user.getEmail()))
            throw new EmailAlreadyFoundException(user.getEmail() + " is taken");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean findEmailExists(String email) {
        Optional<User> foundUser = userRepository.findUserByEmail(email);
        return foundUser.isPresent();
    }

    public boolean findUsernameExists(String username) {
        Optional<User> foundUser = userRepository.findUserByUsername(username);
        return foundUser.isPresent();
    }

}
