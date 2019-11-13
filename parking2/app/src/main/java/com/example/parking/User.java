package com.example.parking;

public class User {
    public String name;
    public String email;
    // Add more fields later

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        // Add defaults values
    }
}
