package com.complex.auction.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    private void createUser() {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@email.com");
        userRepository.save(user);
    }

    @BeforeEach
    void setUp() {
        userRepository.flush();
    }

    @Test
    void findUserByUsername() {
        createUser();

        Optional<User> foundUser = userRepository.findUserByUsername("john");
        assertTrue(foundUser.isPresent());
        User checkUser = foundUser.get();

        assertEquals("john@email.com", checkUser.getEmail());
    }

    @Test
    void findUserByEmail() {
        createUser();

        Optional<User> foundUser = userRepository.findUserByEmail("john@email.com");
        assertTrue(foundUser.isPresent());
        User checkUser = foundUser.get();

        assertEquals("john@email.com", checkUser.getEmail());
    }

    @Test
    void existsByUsername() {
        createUser();
        assertTrue(userRepository.existsByUsername("john"));
    }


}