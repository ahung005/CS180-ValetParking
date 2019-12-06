package com.example.parking;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    private String day, course, time, building;

    private TextView user_name;
    private TextView user_email;
    private TextView permit_type;

    private FirebaseUser user;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        account = GoogleSignIn.getLastSignedInAccount(Profile.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        user_name = findViewById(R.id.userName);
        user_email = findViewById(R.id.userEmail);
        permit_type = findViewById(R.id.permitType);

        user = FirebaseAuth.getInstance().getCurrentUser();
        DisplayName();
        DisplayEmail();
        DisplayPermit();

        Button changePassButton = (Button) findViewById(R.id.changePassButton);

        // Only allow change of password if you're not logged in with gmail (CHANGE GMAIL PASS? lol)
        if (account != null) {
            Log.e("Profile", "onCreate: null" );
            changePassButton.setEnabled(false);
            changePassButton.setAlpha(0.2f);
        } else {
            Log.e("Profile", "onCreate: not null" );

            changePassButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), changePassword.class));
                }
            });
        }

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

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                day = snapshot.getKey();
                                course = snapshot.child("course_name").getValue(String.class);
                                building = snapshot.child("building").getValue(String.class);
                                time = snapshot.child("time").getValue(String.class);
                                TextView display = (TextView) findViewById(getViewId());

                                Log.d("", "onDataChange: " + getViewId());
                                day = day.substring(0, 1).toUpperCase() + day.substring(1);
                                if (course.isEmpty()) {
                                    display.setText(day + ": NONE");
                                } else {
                                    display.setText(day + ": " + course + " " + time + " " + building);
                                }

                            }
                        } else{
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

    private int getViewId()  {
        Resources res = getResources();
        return res.getIdentifier(day.toLowerCase(), "id", "com.example.parking");
    }

    private void DisplayName() {
        FirebaseDatabase.getInstance()                // Get everything
                .getReference("Users")           // From that get Users
                .child(user.getUid())                           // From that get specific uid (class)
                .child("name")                        // From that get that class field "name"
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // If we have name in the specific database location, get the value (from snapshot)
                            user_name.setText("Display name: " + dataSnapshot.getValue());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void DisplayEmail() {
        user_email.setText("Email: " + user.getEmail());
    }

    private void DisplayPermit() {
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("permit")
                .child("type")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            permit_type.setText("Permit Type: " + dataSnapshot.getValue().toString());
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
