package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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

public class Add_Replace extends AppCompatActivity implements OnItemSelectedListener{

    boolean toasted = false;
    private String daySel;
    private String buildingSel;
    private String ClassName;
    private String Time;

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

        final EditText chooseTime = findViewById(R.id.classTime);

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Replace.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();


            }
        });

        final TextView TestResult = (TextView) findViewById(R.id.TestResult);

        Button updateSched = (Button) findViewById(R.id.update);

        updateSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText className = (EditText) findViewById(R.id.Class);
                EditText time = (EditText) findViewById(R.id.classTime);

                ClassName = className.getText().toString();
                Time = time.getText().toString();

                if(ClassName.matches("")){
                    className.requestFocus();
                    className.setError("Required");
                }else if(Time.matches("")){
                    time.requestFocus();
                    time.setError("Required");
                }
                //Event for when update button is pressed
                //needs to update firbase with new selected schedule
                //TestResult.setText(daySel + ", " + ClassName + ", " + Time + ", " + buildingSel);
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.daySpinner)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
            daySel = text;
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
