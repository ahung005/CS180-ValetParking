package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        final TextView notiText1 = (TextView) findViewById(R.id.notiText1);
        final TextView notiText2 = (TextView) findViewById(R.id.notiText2);
        final EditText notiEditText = (EditText) findViewById(R.id.notiEditText);
        final Button notiUpdate = (Button) findViewById(R.id.notiUpdateButton);


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
            }
        });
    }
}
