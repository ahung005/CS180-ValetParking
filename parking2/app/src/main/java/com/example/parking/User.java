package com.example.parking;

public class User {
    public String name;
    public String email;
    public Permit permit;
    public Schedule schedule;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.permit = new Permit("None", "");
        this.schedule = new Schedule(getMonday(), getTuesday(), getWednesday(), getThursday(), getFriday());
    }

    // Change later to factory
    private Schedule.Monday getMonday() {
        return new Schedule.Monday("", "", "");
    }

    private Schedule.Tuesday getTuesday() {
        return new Schedule.Tuesday("", "", "");
    }

    private Schedule.Wednesday getWednesday() {
        return new Schedule.Wednesday("", "", "");
    }

    private Schedule.Thursday getThursday() {
        return new Schedule.Thursday("", "", "");
    }

    private Schedule.Friday getFriday() {
        return new Schedule.Friday("", "", "");
    }
}
