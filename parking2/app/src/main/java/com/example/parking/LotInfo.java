package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LotInfo extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotinfo);

        // Views


        TextView lotname = findViewById(R.id.header);
        String dname = getIntent().getStringExtra("name");
        lotname.setText(dname);

        TextView buildings = findViewById(R.id.email);
        String building = getIntent().getStringExtra("builds");
        buildings.setText("Buildings Near: " + building);


        TextView walktime = findViewById(R.id.password);
        String time = getIntent().getStringExtra("time");
        walktime.setText("Walking Time: " + time);

        final String strUri = getIntent().getStringExtra("maps");

        Button navigate = findViewById(R.id.login);
        navigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        //handle the already login user

    }



    // Could add login with google, twitter, etc. Then send email so they can recover LATER
}