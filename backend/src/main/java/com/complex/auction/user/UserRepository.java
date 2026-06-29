package com.complex.auction.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    boolean existsByUsername(String username);
}