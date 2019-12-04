package com.example.parking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class changePermit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView mdisplayDate;
    private TextView mpermitTextView;
    private DatePickerDialog.OnDateSetListener mdateSetListener;
    private Spinner permitSpinner;

    private Calendar user_date;
    private String permitSel;
    SimpleDateFormat formatter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepermit);

        user_date = Calendar.getInstance();
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        mdisplayDate = findViewById(R.id.datePicker);
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
                user_date.set(year, month, dayOfMonth);
                mdisplayDate.setText(formatter.format(user_date.getTime()));
            }
        };

        // Create an ArrayAdapter using the string array and a default spinner layout
        permitSpinner = findViewById(R.id.permitSpinner);
        mpermitTextView = (TextView)permitSpinner.getSelectedView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,  R.array.permit_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        permitSpinner.setAdapter(adapter);
        permitSpinner.setOnItemSelectedListener(this);

        // Handle the 'UPDATE' button
        final Button updatePermitButton = (Button) findViewById(R.id.updatePermitButton);
        updatePermitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    updatePermitButton.setClickable(false);
                    String type = mpermitTextView.getText().toString();
                    String expiration = mdisplayDate.getText().toString();
                    ChangePermit(type, expiration);
                }
            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        // Check if the user confirmed a date (If it says 'expiration date', false)
        // 2nd check depends on the first one to be false
        if (mdisplayDate.getText().toString().matches("")) {
            ((TextView) findViewById(R.id.datePicker)).requestFocus();
            ((TextView) findViewById(R.id.datePicker)).setError("Required");
            valid = false;
        } else if (user_date.getTime().before(Calendar.getInstance().getTime())) {
            ((TextView) findViewById(R.id.datePicker)).requestFocus();
            ((TextView) findViewById(R.id.datePicker)).setError("Invalid Date");
            valid = false;
        } else
            mdisplayDate.setError(null);

        // Check if user confirmed a parking pass type (else case handles if they corrected their mistake)
        if (permitSel.matches("None")) {
            mpermitTextView.setError("Required");
            mpermitTextView.setTextColor(Color.RED);//just to highlight that this is an error
            mpermitTextView.setText("Required");
            valid = false;
        } else {
            mpermitTextView.setText(permitSel);
        }

        return valid;
    }

    private void ChangePermit(String type, String expiration) {
        // Store our changes with correct path, relative to our updateChildren() command
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Permit permit = new Permit(type, expiration);

        // Make updates
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(user.getUid())
                .child("permit")
                .setValue(permit);

        Toast.makeText(changePermit.this, "Permit successfully changed", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mpermitTextView = (TextView)permitSpinner.getSelectedView();
        String text = parent.getItemAtPosition(position).toString();
        permitSel = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
