package com.complex.auction;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

import com.complex.auction.user.User;
import com.complex.auction.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("john");
        user.setEmail("john@test.com");
        user.setPassword(passwordEncoder.encode("password123"));

        userRepository.save(user);
    }

    @Test
    void loginShouldReturnJwt() throws Exception {
        mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email":"john@test.com",
                                  "password":"password123"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }
}