package com.complex.auction.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    private String email;
    @Size(min = 5, max = 25, message = "Password must be between 3 and 25 characters ")
    private String password;
    private LocalDateTime accountCreated;

    public User(String email, Long id, String password, String username) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.username = username;
        accountCreated = LocalDateTime.now();
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(LocalDateTime accountCreated) {
        this.accountCreated = accountCreated;
    }
}
