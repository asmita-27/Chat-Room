package com.example.chat.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    // Constructors
    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
