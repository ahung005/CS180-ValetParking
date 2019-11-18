package com.example.parking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Button changePassButton = (Button) findViewById(R.id.changePassButton);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), changePassword.class));
            }
        });

        Button changePermitButton = (Button) findViewById(R.id.changePermitButton);
        changePermitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), changePermit.class));
            }
        });

        Button updateScheduleButton = (Button) findViewById(R.id.UpdateSched);
        updateScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateSchedule.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .child("monday")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String day = dataSnapshot.getKey();
                            String course = dataSnapshot.child("course_name").getValue(String.class);
                            String building = dataSnapshot.child("building").getValue(String.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            TextView display = (TextView) findViewById(R.id.monday);

                            if(course.matches("")){
                                display.setText("Monday: NONE" );
                            }else{
                                display.setText("Monday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(Profile.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        ////////////////////Tuseday
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .child("tuesday")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String day = dataSnapshot.getKey();
                            String course = dataSnapshot.child("course_name").getValue(String.class);
                            String building = dataSnapshot.child("building").getValue(String.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            TextView display = (TextView) findViewById(R.id.tuesday);

                            if(course.matches("")){
                                display.setText("Tuesday: NONE" );
                            }else{
                                display.setText("Tuesday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(Profile.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .child("wednesday")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String day = dataSnapshot.getKey();
                            String course = dataSnapshot.child("course_name").getValue(String.class);
                            String building = dataSnapshot.child("building").getValue(String.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            TextView display = (TextView) findViewById(R.id.wednesday);

                            if(course.matches("")){
                                display.setText("Wednesday: NONE" );
                            }else{
                                display.setText("Wednesday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(Profile.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .child("thursday")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String day = dataSnapshot.getKey();
                            String course = dataSnapshot.child("course_name").getValue(String.class);
                            String building = dataSnapshot.child("building").getValue(String.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            TextView display = (TextView) findViewById(R.id.thursday);

                            if(course.matches("")){
                                display.setText("Thursday: NONE" );
                            }else{
                                display.setText("Thursday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(Profile.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .child("friday")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String day = dataSnapshot.getKey();
                            String course = dataSnapshot.child("course_name").getValue(String.class);
                            String building = dataSnapshot.child("building").getValue(String.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            TextView display = (TextView) findViewById(R.id.friday);

                            if(course.matches("")){
                                display.setText("Friday: NONE" );
                            }else{
                                display.setText("Friday " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(Profile.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

}
