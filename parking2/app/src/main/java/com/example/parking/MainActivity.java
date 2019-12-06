package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SignInButton signInButton;
    private GoogleSignInClient signInClient;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private int RC_SIGN = 4444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        //Add listeners to our buttons
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.loginGoogle).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);


        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Google signin
        signInButton = findViewById(R.id.loginGoogle);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("36884769477-8md33sqairpug2m81kbm96med7nf768a.apps.googleusercontent.com")
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
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
            case R.id.loginGoogle:
                startActivityForResult(signInClient.getSignInIntent(), RC_SIGN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String name = account.getDisplayName();
                                    final String email = account.getEmail();
                                    final String password = account.getId();
                                    final DatabaseReference ref = FirebaseDatabase.getInstance()
                                            .getReference("Users");
                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                // If new user, create
                                                if (!dataSnapshot.child(uid).exists()) {
                                                    User user = new User(name, email);
                                                    ref.child(uid).setValue(user);
                                                    mAuth.createUserWithEmailAndPassword(email, password);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), databaseError.toString(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    startActivity(new Intent(MainActivity.this, HomePage.class));
                                }
                            }
                        });

            } catch(ApiException e){
                // If they close the window, do nothing
            }
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

}