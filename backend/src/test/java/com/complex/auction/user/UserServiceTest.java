package com.complex.auction.user;

import com.complex.auction.dto.RegistrationRequest;
import com.complex.auction.exceptions.EmailAlreadyFoundException;
import com.complex.auction.exceptions.UsernameAlreadyFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegistrationRequest registrationRequestPayload;



    @Test
    void saveUser_ShouldSaveUser_WithNoConflicts() {
        // set up
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "john@email.com",
                "john",
                "password123"
        );

        when(passwordEncoder.encode(registrationRequest.password())).thenReturn("hashed123");
        when(userRepository.findUserByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail("john@email.com")).thenReturn(Optional.empty());

        User savedUser = new User(
                "john@email.com",
                "john",
                "hashed123"
        );
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // call test method
        User result = userService.saveUser(registrationRequest);


        //test result output
        assertEquals("john", result.getUsername());
        assertEquals("john@email.com", result.getEmail());
        assertEquals("hashed123", result.getPassword());


    }

    @Test
    void findEmailExists() {
    }

    @Test
    void findUsernameExists() {
    }

    @Test
    void findUser() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void updatePassword() {
    }
}
