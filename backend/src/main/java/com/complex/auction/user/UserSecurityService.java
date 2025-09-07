package com.complex.auction.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        if(user.isEmpty()) throw new UsernameNotFoundException("User not found with email: " + email);

        User foundUser = user.get();
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(foundUser.getEmail())
                .password(foundUser.getPassword())
                .build();
    }
}
