package com.complex.auction.user;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.complex.auction.dto.RegistrationRequest;

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

        
        User result = userService.saveUser(registrationRequest);


        
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
