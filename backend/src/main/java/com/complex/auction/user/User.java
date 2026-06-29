package com.complex.auction.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.complex.auction.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    private String email;

    @Size(min = 5, message = "Password must be between 5 and 25 characters")
    @JsonIgnore
    private String password;

    private LocalDateTime accountCreated;

    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore                
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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public void setId(UUID id) { this.id = id; }
    public UUID getId() { return id; }

    public LocalDateTime getAccountCreated() { return accountCreated; }
    public void setAccountCreated(LocalDateTime accountCreated) { this.accountCreated = accountCreated; }

    public Set<Item> getItemsListed() { return itemsListed; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}