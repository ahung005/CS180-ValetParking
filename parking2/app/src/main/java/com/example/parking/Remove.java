package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Remove extends AppCompatActivity implements OnItemSelectedListener  {

    private String daySel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.day_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.removeButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(user.getUid())
                    .child("schedule")
                    .child(daySel);

            switch (daySel) {
                case "monday":
                    ref.setValue(new Schedule.Monday("", "", ""));
                    break;

                case "tuesday":
                    ref.setValue(new Schedule.Tuesday("", "", ""));
                    break;

                case "wednesday":
                    ref.setValue(new Schedule.Wednesday("", "", ""));
                    break;

                case "thursday":
                    ref.setValue(new Schedule.Thursday("", "", ""));
                    break;

                case "friday":
                    ref.setValue(new Schedule.Friday("", "", ""));
                    break;

                default:
                    break;
            }

            Toast.makeText(Remove.this, "Class successfully removed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Remove.this, HomePage.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        daySel = text.toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
