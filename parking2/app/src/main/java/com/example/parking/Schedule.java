package com.example.parking;

import android.text.style.TtsSpan;

// We can use Hashmap later if we decide to change only first class (store first class)
// Remember that we can't account for a morning and night class if morning class is dropped
// or for holidays
public class Schedule {
    // For now, i guess these will store the first class
    public String Monday, Tuesday, Wednesday, Thursday, Friday;

    public Schedule() {
        // Default constructor required for calls to DataSnapshot.getValue(Schedule.class)
    }

    public Schedule(String Monday, String Tuesday, String Wednesday, String Thursday, String Friday) {
        this.Monday = Monday;
        this.Tuesday = Tuesday;
        this.Wednesday = Wednesday;
        this.Thursday = Thursday;
        this.Friday = Friday;
    }


}
