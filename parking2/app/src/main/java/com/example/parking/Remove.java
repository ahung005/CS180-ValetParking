package com.example.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Remove extends AppCompatActivity implements OnItemSelectedListener{

    boolean toasted = false;
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

        Button removeButton = (Button) findViewById(R.id.removeButton);
        final TextView TestResult2 = (TextView) findViewById(R.id.testResult2);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Event for when update button is pressed
                //needs to update firbase to remove class
                //TestResult2.setText(daySel);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
        daySel = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
