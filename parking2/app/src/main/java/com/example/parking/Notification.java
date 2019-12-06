package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notification extends AppCompatActivity {

    private Switch switch1;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        user = FirebaseAuth.getInstance().getCurrentUser();
        switch1 = (Switch) findViewById(R.id.switch1);
        setSwitch();
        final TextView notiText1 = (TextView) findViewById(R.id.notiText1);
        final TextView notiText2 = (TextView) findViewById(R.id.notiText2);
        final EditText notiEditText = (EditText) findViewById(R.id.notiEditText);
        final Button notiUpdate = (Button) findViewById(R.id.notiUpdateButton);


        notiText2.setVisibility(View.GONE);
        notiEditText.setVisibility(View.GONE);
        notiUpdate.setVisibility(View.GONE);

        setSwitch();
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    notiText2.setVisibility(View.VISIBLE);
                    notiEditText.setVisibility(View.VISIBLE);
                    notiUpdate.setVisibility(View.VISIBLE);
                }
                else {
                    notiText2.setVisibility(View.GONE);
                    notiEditText.setVisibility(View.GONE);
                    notiUpdate.setVisibility(View.GONE);
                }
                changeSwitch(isChecked);
            }
        });

        notiUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String hours = notiEditText.getText().toString();
                if (TextUtils.isEmpty(hours)) {
                    notiEditText.setError("Required.");
                } else {
                    notiEditText.setError(null);
                    changeHours(notiEditText.getText().toString());
                }

            }
        });
    }

    private void setSwitch() {
        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("notifications")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            switch1.setChecked(((String)dataSnapshot.getValue()).matches("ON"));
                        }
                        else {
                            Log.e("Notification", "getNoficationSwitchVal: Error: notifications val missing!");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Notification.this, databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void changeSwitch(boolean isChecked) {
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(user.getUid())
                .child("notifications")
                .setValue(isChecked ? "ON" : "OFF");
    }

    private void changeHours(String hours) {
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(user.getUid())
                .child("notificationHours")
                .setValue(hours);
        Toast.makeText(Notification.this, "Hours before your notifications changed!",
                Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }
}
