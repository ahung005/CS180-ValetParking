package com.example.parking;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class changePermit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "changePermit";
    boolean toasted = false;
    private TextView mdisplayDate;
    private DatePickerDialog.OnDateSetListener mdateSetListener;

    private String date;
    private String permitSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepermit);

        mdisplayDate = (TextView) findViewById(R.id.datePicker);
        mdisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                 int year = cal.get(Calendar.YEAR);
                 int month = cal.get(Calendar.MONTH);
                 int day = cal.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog dialog = new DatePickerDialog(changePermit.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mdateSetListener, year, month, day);
                 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 dialog.show();

            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;

                date = "   " + month + "/" + dayOfMonth + "/" + year;
                mdisplayDate.setText(date );
            }
        };

        Spinner permitSpinner = (Spinner) findViewById(R.id.permitSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.permit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        permitSpinner.setAdapter(adapter);
        permitSpinner.setOnItemSelectedListener(this);


        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        Button updatePermitButton = (Button) findViewById(R.id.updatePermitButton);
        updatePermitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Event for when update button is pressed
                //needs to update firbase with new selected permit
                resultTextView.setText(permitSel + "         " + date);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
        permitSel = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
