package com.complex.auction.user;

import com.complex.auction.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    private String email;

    @Size(min = 5, message = "Password must be between 5 and 25 characters ")
    private String password;

    private LocalDateTime accountCreated;

    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> itemsListed;

    public User(String email, String username, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
        accountCreated = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        itemsListed = new HashSet<>();
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

    public Set<Item> getItemsListed() {
        return itemsListed;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
