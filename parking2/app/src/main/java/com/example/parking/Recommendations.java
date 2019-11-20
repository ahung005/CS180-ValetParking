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
                        if (permitType.equals("Gold")) {
                            Log.d("TAG", "I have a gold permit");
                            FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(user.getUid())
                                .child("schedule")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                day = snapshot.getKey();
                                                course_name = snapshot.child("course_name").getValue(String.class);
                                                time = snapshot.child("time").getValue(String.class);
                                                building = snapshot.child("building").getValue(String.class);
                                                tv = findViewById(getScheduleViewId());
                                                tv2 = findViewById(getLotViewId());

                                                if (!day.isEmpty())
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
                        }
                        else if (permitType.equals("Red") || permitType.equals("Blue")) {
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
                        }
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
            if (building.equals("UV Theatre") || building.equals("Olmsted")
                    || building.equals("Boyce") || building.equals("Life Sciences")
                    || building.equals("Watkins") || building.equals("Sproul") ||
                    building.equals("Pierce") || building.equals("Spieth")) {
                recommendedLot = "Lot 32";
            } else if (building.equals("Hum/SS")) {
                recommendedLot = "Lot 30";
            } else if (building.equals("Skye") || building.equals("MSE")
                    || building.equals("UNLH") || building.equals("CHASS North")
                    || building.equals("Bourns") || building.equals("Chung")
                    || building.equals("CHASS South") || building.equals("Physics")) {
                recommendedLot = "Lot 50";
            }
        } else {
            if (building.equals("UV Theatre") || building.equals("Olmsted")
                    || building.equals("Boyce") || building.equals("Life Sciences")
                    || building.equals("Watkins") || building.equals("Sproul") ||
                    building.equals("Pierce") || building.equals("Spieth")) {
                recommendedLot = "Lot 32";
            } else if (building.equals("Hum/SS")) {
                recommendedLot = "Lot 30";
            } else if (building.equals("Skye") || building.equals("MSE")
                    || building.equals("UNLH") || building.equals("CHASS North")
                    || building.equals("Bourns") || building.equals("Chung")
                    || building.equals("CHASS South") || building.equals("Physics")) {
                recommendedLot = "Lot 50";
            }
        }
    }
}
