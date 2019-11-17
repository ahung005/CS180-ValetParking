package com.example.parking;

import android.text.style.TtsSpan;

// We can use Hashmap later if we decide to change only first class (store first class)
// Remember that we can't account for a morning and night class if morning class is dropped
// or for holidays
public class Schedule
{
    public Monday monday;
    public Tuesday tuesday;
    public Wednesday wednesday;
    public Thursday thursday;
    public Friday friday;

    public Schedule() {

    }

    public Schedule(Monday mon, Tuesday tue, Wednesday wed, Thursday thur, Friday fri) {
        this.monday = mon;
        this.tuesday = tue;
        this.wednesday = wed;
        this.thursday = thur;
        this.friday = fri;
    }
    public static class Monday extends Weekday
    {
        public Monday() {
            // Default constructor required for calls to DataSnapshot.getValue(Monday.class)
        }

        public Monday(String course_name, String time, String building) {
            super(course_name, time, building);
        }
    }

    public static class Tuesday extends Weekday
    {
        public Tuesday() {
            // Default constructor required for calls to DataSnapshot.getValue(Monday.class)
        }

        public Tuesday(String course_name, String time, String building) {
            super(course_name, time, building);
        }
    }

    public static class Wednesday extends Weekday
    {
        public Wednesday() {
            // Default constructor required for calls to DataSnapshot.getValue(Monday.class)
        }

        public Wednesday(String course_name, String time, String building) {
            super(course_name, time, building);
        }
    }

    public static class Thursday extends Weekday
    {
        public Thursday() {
            // Default constructor required for calls to DataSnapshot.getValue(Monday.class)
        }

        public Thursday(String course_name, String time, String building) {
            super(course_name, time, building);
        }
    }

    public static class Friday extends Weekday
    {
        public Friday() {
            // Default constructor required for calls to DataSnapshot.getValue(Monday.class)
        }

        public Friday(String course_name, String time, String building) {
            super(course_name, time, building);
        }
    }

    public static class Weekday
    {
        public String course_name, time, building;

        public Weekday() {

        }

        public Weekday(String course_name, String time, String building) {
            this.course_name = course_name;
            this.time = time;
            this.building = building;
        }
    }
}
