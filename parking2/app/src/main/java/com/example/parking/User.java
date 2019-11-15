package com.example.parking;

public class User {
    public enum PERMIT_TYPE { NONE, GOLD, GOLD_PLUS, NIGHT, ORGANGE, MOTORCYCLE }

    public String name;
    public String email;
    public PERMIT_TYPE permit;
    public Schedule schedule;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.permit = PERMIT_TYPE.NONE;
        this.schedule = new Schedule();
    }
}
