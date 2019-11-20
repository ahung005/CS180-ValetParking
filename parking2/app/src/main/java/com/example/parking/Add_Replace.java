package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Replace extends AppCompatActivity implements OnItemSelectedListener{

    private String daySel;
    private String buildingSel;
    private String ClassName;
    private String Time;
    private String TimeFormat;

    private EditText className;
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__replace);

        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.day_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        daySpinner.setAdapter(adapter);
        daySpinner.setOnItemSelectedListener(this);

        Spinner buildingSpinner = (Spinner) findViewById(R.id.buildingSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,  R.array.building_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        buildingSpinner.setAdapter(adapter2);
        buildingSpinner.setOnItemSelectedListener(this);

        final TextView chooseTime = findViewById(R.id.classTime);

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Replace.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int mins) {
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            TimeFormat = "AM";
                        } else if (hourOfDay == 12) {
                            TimeFormat = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            TimeFormat = "PM";
                        } else {
                            TimeFormat = "AM";
                        }

                        String minutes = "";
                        if (mins < 10 ) {
                            minutes = "0" + mins;
                        }
                        chooseTime.setText(hourOfDay + ":" + (minutes.isEmpty() ? mins : minutes) + " " + TimeFormat);
                    }
                }, 0, 0, false);
                //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                timePickerDialog.show();


            }
        });

        final TextView TestResult = (TextView) findViewById(R.id.TestResult);

        Button updateSched = (Button) findViewById(R.id.update);

        updateSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 className = findViewById(R.id.Class);
                 time = findViewById(R.id.classTime);

                ClassName = className.getText().toString().replaceAll("\\s+", "").toUpperCase();
                Time = time.getText().toString().replaceAll("\\s+", "");;

                //TestResult.setText(daySel + ", " + ClassName + ", " + Time + ", " + buildingSel);

                if (validateForm()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(user.getUid())
                            .child("schedule")
                            .child(daySel.toLowerCase());

                    switch (daySel) {
                        case "monday":
                            ref.setValue(new Schedule.Monday(ClassName, Time, buildingSel));
                            break;

                        case "tuesday":
                            ref.setValue(new Schedule.Tuesday(ClassName, Time, buildingSel));
                            break;

                        case "wednesday":
                            ref.setValue(new Schedule.Wednesday(ClassName, Time, buildingSel));
                            break;

                        case "thursday":
                            ref.setValue(new Schedule.Thursday(ClassName, Time, buildingSel));
                            break;

                        case "friday":
                            ref.setValue(new Schedule.Friday(ClassName, Time, buildingSel));
                            break;

                        default:
                            Toast.makeText(Add_Replace.this, "FAILED", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Add_Replace.this, HomePage.class));
                            break;
                    }

                    Toast.makeText(Add_Replace.this, "Schedule change saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_Replace.this, HomePage.class));
                }

            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ClassName);
        if (matcher.find()) {
            className.requestFocus();
            className.setError("No special characters");
            valid = false;
        }
        else if (ClassName.isEmpty()) {
            className.requestFocus();
            className.setError("Required");
            valid = false;
        } else if (ClassName.length() < 3) {
            className.requestFocus();
            className.setError("Should be longer");
            valid = false;
        } else
            className.setError(null);

        if(Time.isEmpty()){
            time.requestFocus();
            time.setError("Required");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.daySpinner)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
            daySel = text.toLowerCase();
        }
        else if(parent.getId() == R.id.buildingSpinner)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
            buildingSel = text;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
