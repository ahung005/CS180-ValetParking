package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);



        Button add_replaceButton = (Button) findViewById(R.id.Add_Replace);
        add_replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Add_Replace.class));
            }
        });

        Button removeButton = (Button) findViewById(R.id.Remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Remove.class));
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
                            TextView display = (TextView) findViewById(R.id.monday2);

                            if(course.matches("")){
                                display.setText("Monday: NONE" );
                            }else{
                                display.setText("Monday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(UpdateSchedule.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                         Toast.makeText(UpdateSchedule.this, databaseError.toString(),
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
                            TextView display = (TextView) findViewById(R.id.tuesday2);

                            if(course.matches("")){
                                display.setText("Tuesday: NONE" );
                            }else{
                                display.setText("Tuesday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(UpdateSchedule.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpdateSchedule.this, databaseError.toString(),
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
                            TextView display = (TextView) findViewById(R.id.wednesday2);

                            if(course.matches("")){
                                display.setText("Wednesday: NONE" );
                            }else{
                                display.setText("Wednesday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(UpdateSchedule.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpdateSchedule.this, databaseError.toString(),
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
                            TextView display = (TextView) findViewById(R.id.thursday2);

                            if(course.matches("")){
                                display.setText("Thursday: NONE" );
                            }else{
                                display.setText("Thursday: " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(UpdateSchedule.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpdateSchedule.this, databaseError.toString(),
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
                            TextView display = (TextView) findViewById(R.id.friday2);

                            if(course.matches("")){
                                display.setText("Friday: NONE" );
                            }else{
                                display.setText("Friday " + course + " " + time + " " + building);
                            }

                        }
                        else {
                            Toast.makeText(UpdateSchedule.this, "Shit don't exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UpdateSchedule.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });


    }
}
