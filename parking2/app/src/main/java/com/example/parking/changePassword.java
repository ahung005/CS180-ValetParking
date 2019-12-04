package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassword extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText, newPasswordEditText2;
    private String oldPass, newPass, newPass2;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);


        final Button passwordButton = findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPasswordEditText =  findViewById(R.id.oldPasswordEditText);
                newPasswordEditText =  findViewById(R.id.newPasswordEditText);
                newPasswordEditText2 =  findViewById(R.id.newPasswordEditText2);

                if(validateForm()) {
                    passwordButton.setClickable(false);
                    ChangePass(oldPass, newPass);
                }
            }
        });

    }

    // Check basic cases like different old-new passwords, non-empty(s), matching new passwords
    // The reason that there is also a *.setError(null) is in case there was an error but they fixed it
    // so we would remove that error text
    private boolean validateForm() {
        boolean valid = true;

        oldPass = oldPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(oldPass)) {
            oldPasswordEditText.setError("Required.");
            valid = false;
        } else {
            oldPasswordEditText.setError(null);
        }

        newPass = newPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(newPass)) {
            newPasswordEditText.setError("Required.");
            valid = false;
        } else {
            newPasswordEditText.setError(null);
        }

        newPass2 = newPasswordEditText2.getText().toString();
        if (TextUtils.isEmpty(newPass2)) {
            newPasswordEditText2.setError("Required.");
            valid = false;
        } else {
            newPasswordEditText2.setError(null);
        }

        if (!newPass.equals(newPass2)) {
            newPasswordEditText.setError("Passwords don't match.");
            valid = false;
        } else {
            newPasswordEditText.setError(null);
        }

        if (newPass.equals(oldPass)) {
            newPasswordEditText.setError("Same new and old password.");
        } else {
            newPasswordEditText.setError(null);
        }

        return valid ;
    }


    private void ChangePass(final String old_pass, final String new_pass) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, old_pass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password successfully modified",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Password change unsuccessful",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect old password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
