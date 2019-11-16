package com.example.parking;

public class Permit {
    public String type;
    public String expiration;


    public Permit() {
        // Default constructor required for calls to DataSnapshot.getValue(Permit.class)
    }

    public Permit(String type, String expiration) {
        this.type = type;
        this.expiration = expiration;
    }


}
