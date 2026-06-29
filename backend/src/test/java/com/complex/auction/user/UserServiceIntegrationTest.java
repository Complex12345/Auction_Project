package com.complex.auction.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.complex.auction.dto.RegistrationRequest;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser_ShouldPersistUserToDatabase() {

        RegistrationRequest request = new RegistrationRequest(
                "john@email.com",
                "john",
                "password123"
        );

        User savedUser = userService.saveUser(request);

        assertNotNull(savedUser.getId());
        assertEquals("john", savedUser.getUsername());
        assertEquals("john@email.com", savedUser.getEmail());

        assertNotEquals("password123", savedUser.getPassword());

        User dbUser = userRepository.findUserByUsername("john").orElseThrow();

        assertEquals(savedUser.getId(), dbUser.getId());
        assertEquals("john@email.com", dbUser.getEmail());
        assertEquals(savedUser.getPassword(), dbUser.getPassword());
    }
}