package com.example.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class changePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);


        Button passwordButton = (Button) findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText oldPasswordEditText = (EditText) findViewById(R.id.oldPasswordEditText);
                EditText newPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
                EditText newPasswordEditText2 = (EditText) findViewById(R.id.newPasswordEditText2);


                TextView oldPasswordTextView = (TextView) findViewById(R.id.oldPasswordTextView);
                TextView newPasswordTextView = (TextView) findViewById(R.id.newPasswordTextView);


                String oldPass = oldPasswordEditText.getText().toString();
                String newPass = newPasswordEditText.getText().toString();
                String newPass2 = newPasswordEditText2.getText().toString();

                oldPasswordTextView.setText(oldPass);

                if(newPass.equals(newPass2)) {
                    newPasswordTextView.setText(newPass);
                }
                else {
                    newPasswordTextView.setText("Try Again");
                }

            }
        });

    }

}
