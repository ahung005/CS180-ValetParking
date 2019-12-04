package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Recommendations extends AppCompatActivity  {
    private String day; // stores the day of the class
    private String course_name; //stores the name of the class
    private String time; //stores the time for the class
    private String building; //stores the name of the building for class
    private String recommendedLot; // stores the closest recommended lot based on the building name
    private String permitType;

    // Textviews
    private TextView tv; //textView to display the schedule in the format (Day: , className, time, building)
    private TextView tv2; //textView to display the recommended parking lots based on permit type and buildings

    // Hold values of spaces for parking lots
    private Bundle bundle;

    // Current user
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRecommendation();

        // Contains values of parking lot spaces
        bundle = getIntent().getExtras();
    }

    public void setRecommendation()  {
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance()
            .getReference("Users")
             .child(user.getUid())
            .child("permit")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        permitType = dataSnapshot.child("type").getValue(String.class);
                        Log.d("TAG", "I have a" + permitType +  "permit");
                        FirebaseDatabase.getInstance()
                                .getReference("Users").child(user.getUid())
                                .child("schedule")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                day = snapshot.getKey().toLowerCase();
                                                course_name = snapshot.child("course_name").getValue(String.class);
                                                time = snapshot.child("time").getValue(String.class);
                                                building = snapshot.child("building").getValue(String.class);
                                                tv = findViewById(getScheduleViewId());
                                                tv2 = findViewById(getLotViewId());

                                                day = day.substring(0, 1).toUpperCase() + day.substring(1);
                                                if (!course_name.isEmpty()) {
                                                    tv.setText(day + ": " + course_name + ", " + time + ", " + building);
                                                } else {
                                                    tv.setText(day + ": None");
                                                    tv2.setText("Recommended Lot: None");
                                                    continue;
                                                }
                                                getRecommendedLot();
                                                tv2.setText("Recommended Lot: " + recommendedLot);
                                            }
                                        } else {
                                            Toast.makeText(Recommendations.this, "Doesn't Exist",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(Recommendations.this, databaseError.toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Log.d("TAG", "Permit does not work");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("TAG", "Does not work at all");
                }
            });
    }

    private int getScheduleViewId()  {
        Resources res = getResources();
        return res.getIdentifier(day + "Schedule", "id", "com.example.parking");
    }

    private int getLotViewId() {
        Resources res = getResources();
        return res.getIdentifier(day + "RecommendedLot", "id", "com.example.parking");
    }

    private void getRecommendedLot() {
        if (permitType.equals("Gold")) {
            String F30 = "";
            String F32 = "";
            String F50 = "";
            String F26 = "";
            if (getIntent().getStringExtra("lot_30").matches("FULL")){
                F30 = "30, ";
            }
            if (getIntent().getStringExtra("lot_32").matches("FULL")){
                F32 = "32, ";
            }
            if (getIntent().getStringExtra("lot_50").matches("FULL")){
                F50 = "50, ";
            }
            if (getIntent().getStringExtra("lot_26").matches("FULL")){
                F26 = "26, ";
            }
            if (F26.matches("26, ") || F50.matches("50, ") || F32.matches("32, ") || F30.matches("30, ")){
                String Lots = F26 + F30 + F32 + F50;
                TextView fullWarn = findViewById(R.id.fullWarn);
                fullWarn.setText("Lot " + Lots.substring(0,Lots.length() - 2) + " is Full");
                //Toast.makeText(Recommendations.this,"Lot " + Lots.substring(0,Lots.length() - 2) + " is Full",
                //        Toast.LENGTH_LONG).show();
            }
            if ( building.equals("Olmsted")
                    || building.equals("Boyce") || building.equals("Life Sciences")
                    || building.equals("Pierce") || building.equals("Spieth")) {
                if(getIntent().getStringExtra("lot_32").matches("FULL")){
                    if (getIntent().getStringExtra("lot_30").matches("FULL")){
                        if (getIntent().getStringExtra("lot_50").matches("FULL")){
                            if (getIntent().getStringExtra("lot_26").matches("FULL")){
                                recommendedLot = "None";
                            }else{
                                recommendedLot = "Lot 26";
                            }
                        }else{
                            recommendedLot = "Lot 50";
                        }
                    }else{
                        recommendedLot = "Lot 30";
                    }
                }else{
                    recommendedLot = "Lot 32";
                }
            } else if ( building.equals("Watkins") || building.equals("Sproul") || building.equals("Hum/SS")) {
                if (getIntent().getStringExtra("lot_30").matches("FULL")){
                    if (getIntent().getStringExtra("lot_32").matches("FULL")){
                        if (getIntent().getStringExtra("lot_50").matches("FULL")){
                            if (getIntent().getStringExtra("lot_26").matches("FULL")){
                                recommendedLot = "None";
                            }else{
                                recommendedLot = "Lot26";
                            }
                        }else{
                            recommendedLot = "Lot 50";
                        }
                    }else{
                        recommendedLot = "Lot 32";
                    }
                }else{
                    recommendedLot = "Lot 30";
                }
            } else if (building.equals("UV Theatre") || building.equals("Skye") || building.equals("MSE")
                    || building.equals("UNLH") || building.equals("CHASS North")
                    || building.equals("Bourns") || building.equals("Chung")
                    || building.equals("CHASS South") || building.equals("Physics")) {
                if (getIntent().getStringExtra("lot_50").matches("FULL")){
                    if (getIntent().getStringExtra("lot_30").matches("FULL")){
                        if (getIntent().getStringExtra("lot_32").matches("FULL")){
                            if (getIntent().getStringExtra("lot_26").matches("FULL")){
                                recommendedLot = "None";
                            }else {
                                recommendedLot = "Lot 26";
                            }
                        }else {
                            recommendedLot = "Lot 32";
                        }
                    }else {
                        recommendedLot = "Lot 30";
                    }
                }else{
                    recommendedLot = "Lot 50";
                }
            }
        } else {
            String F6 = "";
            String F24 = "";
            if (getIntent().getStringExtra("lot_6").matches("FULL")){
                F6 = "6, ";
            }
            if (getIntent().getStringExtra("lot_24").matches("FULL")){
                F24 = "24, ";
            }
            if(F6.matches("6, ") || F24.matches("24, ")){
                String Lots = F6 + F24;
                TextView fullWarn = findViewById(R.id.fullWarn);
                fullWarn.setText("Lot " + Lots.substring(0,Lots.length() - 2) + " is Full");
                //Toast.makeText(Recommendations.this,"Lot " + Lots.substring(0,Lots.length() - 2) + " is Full",
                //        Toast.LENGTH_LONG).show();
            }
            if ( building.equals("Spieth") || building.equals("Olmsted") || building.equals("Life Sciences")
                    || building.equals("Sproul") || building.equals("Hum/SS") || building.equals("Watkins")) {
                if (getIntent().getStringExtra("lot_6").matches("FULL")){
                    if (getIntent().getStringExtra("lot_24").matches("FULL")){
                        recommendedLot = "None";
                    }else{
                        recommendedLot = "Lot 24";
                    }
                }else {
                    recommendedLot = "Lot 6";
                }
            } else if (building.equals("UV Theatre") || building.equals("Boyce") || building.equals("Skye") || building.equals("MSE")
                    || building.equals("UNLH") || building.equals("CHASS North")
                    || building.equals("Bourns") || building.equals("Chung")
                    || building.equals("CHASS South") || building.equals("Physics")  || building.equals("Pierce") ) {
                if (getIntent().getStringExtra("lot_24").matches("FULL")){
                    if (getIntent().getStringExtra("lot_6").matches("FULL")){
                        recommendedLot = "None";
                    }else{
                        recommendedLot = "Lot 6";
                    }
                }else {
                    recommendedLot = "Lot 24";
                }
            }
        }
    }
}
