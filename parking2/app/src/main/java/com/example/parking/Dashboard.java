package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Dashboard extends AppCompatActivity {
    private TextView dash;
    private String permitType;
    private String expirationDate;
    private String username;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayPermitInfo();
    }

    public void displayPermitInfo()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            permitType = dataSnapshot.child("permit").child("type").getValue(String.class);
                            expirationDate = dataSnapshot.child("permit").child("expiration").getValue(String.class);
                            username = dataSnapshot.child("name").getValue(String.class);

                            dash = (TextView) findViewById(R.id.permitColor);
                            dash.setText("Permit Type for " + username + ": " + permitType);

                            dash = (TextView) findViewById(R.id.expiration);
                            dash.setText("Expiration Date: " + expirationDate);
                        }
                        else
                        {
                            Toast.makeText(Dashboard.this, "Doesn't Exist",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Dashboard.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
