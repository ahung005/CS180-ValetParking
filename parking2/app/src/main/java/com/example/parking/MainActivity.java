package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        //Add listeners to our buttons
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        //handle the already login user
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            finish();   // Destroy activity MainActivity and not exist in Back stack
        }
    }

    // We are implementing the view onclicklistener so after ADDING the onclicklistener, just wait here
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                LoginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                break;
            case R.id.register:
                startActivity(new Intent(getApplicationContext(), Register.class));
                break;
        }
    }

    // Check email, password that are entered in your Email and Pass views
    // The response will tell you whether the signIn was successful or not
    // Disable button if successful (avoid issues with multiple clicks)
    // Cases: Success, Failure
    private void LoginUser(String email, String password) {
        if (validateForm()) {
            (findViewById(R.id.login)).setClickable(false);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }

    // Simple check on Email and Password views to make sure neither are empty
    // The reason that there is also a *.setError(null) is in case there was an error but they fixed it
    // so we would remove that error text
    private boolean validateForm() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }


    // Could add login with google, twitter, etc. Then send email so they can recover LATER
}