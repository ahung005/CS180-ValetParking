package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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


public class UpdateSchedule extends AppCompatActivity {

    private String day, course, time, building;

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

        // Display schedule
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                day = snapshot.getKey();
                                course = snapshot.child("course_name").getValue(String.class);
                                building = snapshot.child("building").getValue(String.class);
                                time = snapshot.child("time").getValue(String.class);
                                TextView display = findViewById(getDisplayViewId());

                                day = day.substring(0, 1).toUpperCase() + day.substring(1);
                                if (course.isEmpty()) {
                                    display.setText(day + ": NONE");
                                } else {
                                    display.setText(day + ": " + course + " " + time + " " + building);
                                }
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

    private int getDisplayViewId()  {
        Resources res = getResources();
        return res.getIdentifier(day.toLowerCase() + "2", "id", "com.example.parking");
    }
}
