package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Recommendations extends AppCompatActivity {
    private String className; //stores the name of the class
    private String time; //stores the time for the class
    private String building; //stores the name of the building for class
    private String recommendedLot; // stores the closest recommended lot based on the building name
    TextView tv; //textView to display the schedule in the format (Day: , className, time, building)
    TextView tv2; //textView to display the recommended parking lots based on permit type and buildings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setRecommendation();
    }

    public void setRecommendation() {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid())
                .child("permit")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            String permitType = dataSnapshot.child("type").getValue(String.class);
                                if(permitType.equals("Gold"))
                                {
                                    Log.d("TAG", "I have a gold permit");
                                    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();

                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("monday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot mondaySnapshot) {
                                                    if(mondaySnapshot.exists()) {

                                                        tv = (TextView) findViewById(R.id.mondaySchedule);

                                                        String monday = mondaySnapshot.getKey();
                                                        String mondayCourse_name = mondaySnapshot.child("course_name").getValue(String.class);
                                                        String mondayTime = mondaySnapshot.child("time").getValue(String.class);
                                                        String mondayBuilding = mondaySnapshot.child("building").getValue(String.class);

                                                        if(!mondayCourse_name.isEmpty()) {
                                                            tv.setText("Monday: " + mondayCourse_name + ", " + mondayTime + ", " + mondayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Monday: None" );

                                                            tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");

                                                        }

                                                        tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);


                                                        if(mondayBuilding.equals("UV Theatre") ||mondayBuilding.equals("Olmsted")
                                                                || mondayBuilding.equals("Boyce") || mondayBuilding.equals("Life Sciences")
                                                                || mondayBuilding.equals("Watkins") || mondayBuilding.equals("Sproul") ||
                                                                mondayBuilding.equals("Pierce") || mondayBuilding.equals("Spieth"))
                                                        {
                                                            recommendedLot = "Lot 32";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(mondayBuilding.equals("Hum/SS"))
                                                        {
                                                            recommendedLot = "Lot 30";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(mondayBuilding.equals("Skye") || mondayBuilding.equals("MSE")
                                                                || mondayBuilding.equals("UNLH") || mondayBuilding.equals("CHASS North")
                                                                || mondayBuilding.equals("Bourns") || mondayBuilding.equals("Chung")
                                                                || mondayBuilding.equals("CHASS South") || mondayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 50";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }


                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                        .getReference("Users")
                                        .child(user2.getUid())
                                        .child("schedule")
                                        .child("tuesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot tuesdaySnapshot) {
                                                    if(tuesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.tuesdaySchedule);

                                                        String tuesday = tuesdaySnapshot.getKey();
                                                        String tuesdayCourse_name = tuesdaySnapshot.child("course_name").getValue(String.class);
                                                        String tuesdayTime = tuesdaySnapshot.child("time").getValue(String.class);
                                                        String tuesdayBuilding = tuesdaySnapshot.child("building").getValue(String.class);

                                                        if(!tuesdayCourse_name.isEmpty()) {
                                                            tv.setText("Tuesday: " + tuesdayCourse_name + ", " + tuesdayTime + ", " + tuesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Tuesday: None");

                                                            tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);

                                                        if(tuesdayBuilding.equals("UV Theatre") ||tuesdayBuilding.equals("Olmsted")
                                                                || tuesdayBuilding.equals("Boyce") || tuesdayBuilding.equals("Life Sciences")
                                                                || tuesdayBuilding.equals("Watkins") || tuesdayBuilding.equals("Sproul") ||
                                                                tuesdayBuilding.equals("Pierce") || tuesdayBuilding.equals("Spieth"))
                                                        {
                                                            recommendedLot = "Lot 32";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(tuesdayBuilding.equals("Hum/SS"))
                                                        {
                                                            recommendedLot = "Lot 30";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(tuesdayBuilding.equals("Skye") || tuesdayBuilding.equals("MSE")
                                                                || tuesdayBuilding.equals("UNLH") || tuesdayBuilding.equals("CHASS North")
                                                                || tuesdayBuilding.equals("Bourns") || tuesdayBuilding.equals("Chung")
                                                                || tuesdayBuilding.equals("CHASS South") || tuesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 50";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }



                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("wednesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot wednesdaySnapshot) {
                                                    if(wednesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.wednesdaySchedule);

                                                        String wednesday = wednesdaySnapshot.getKey();
                                                        String wednesdayCourse_name = wednesdaySnapshot.child("course_name").getValue(String.class);
                                                        String wednesdayTime = wednesdaySnapshot.child("time").getValue(String.class);
                                                        String wednesdayBuilding = wednesdaySnapshot.child("building").getValue(String.class);

                                                        if(!wednesdayCourse_name.isEmpty()) {
                                                            tv.setText("Wednesday: " + wednesdayCourse_name + ", " + wednesdayTime + ", " + wednesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Wednesday: None");

                                                            tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);

                                                        if(wednesdayBuilding.equals("UV Theatre") ||wednesdayBuilding.equals("Olmsted")
                                                                || wednesdayBuilding.equals("Boyce") || wednesdayBuilding.equals("Life Sciences")
                                                                || wednesdayBuilding.equals("Watkins") || wednesdayBuilding.equals("Sproul") ||
                                                                wednesdayBuilding.equals("Pierce") || wednesdayBuilding.equals("Spieth"))
                                                        {
                                                            recommendedLot = "Lot 32";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(wednesdayBuilding.equals("Hum/SS"))
                                                        {
                                                            recommendedLot = "Lot 30";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(wednesdayBuilding.equals("Skye") || wednesdayBuilding.equals("MSE")
                                                                || wednesdayBuilding.equals("UNLH") || wednesdayBuilding.equals("CHASS North")
                                                            || wednesdayBuilding.equals("Bourns") || wednesdayBuilding.equals("Chung")
                                                            || wednesdayBuilding.equals("CHASS South") || wednesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 50";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }

                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("thursday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot thursdaySnapshot) {
                                                    if(thursdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.thursdaySchedule);

                                                        String thursday = thursdaySnapshot.getKey();
                                                        String thursdayCourse_name = thursdaySnapshot.child("course_name").getValue(String.class);
                                                        String thursdayTime = thursdaySnapshot.child("time").getValue(String.class);
                                                        String thursdayBuilding = thursdaySnapshot.child("building").getValue(String.class);

                                                        if(!thursdayCourse_name.isEmpty()) {
                                                            tv.setText("Thursday: " + thursdayCourse_name + ", " + thursdayTime + ", " + thursdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Thursday: None");

                                                            tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);

                                                        if(thursdayBuilding.equals("UV Theatre") || thursdayBuilding.equals("Olmsted")
                                                                || thursdayBuilding.equals("Boyce") || thursdayBuilding.equals("Life Sciences")
                                                                || thursdayBuilding.equals("Watkins") || thursdayBuilding.equals("Sproul") ||
                                                                thursdayBuilding.equals("Pierce") || thursdayBuilding.equals("Spieth"))
                                                        {
                                                            recommendedLot = "Lot 32";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(thursdayBuilding.equals("Hum/SS"))
                                                        {
                                                            recommendedLot = "Lot 30";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(thursdayBuilding.equals("Skye") || thursdayBuilding.equals("MSE")
                                                                || thursdayBuilding.equals("UNLH") || thursdayBuilding.equals("CHASS North")
                                                                || thursdayBuilding.equals("Bourns") || thursdayBuilding.equals("Chung")
                                                                || thursdayBuilding.equals("CHASS South") || thursdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 50";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }


                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("friday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot fridaySnapshot) {
                                                    if(fridaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.fridaySchedule);

                                                        String friday = fridaySnapshot.getKey();
                                                        String fridayCourse_name = fridaySnapshot.child("course_name").getValue(String.class);
                                                        String fridayTime = fridaySnapshot.child("time").getValue(String.class);
                                                        String fridayBuilding = fridaySnapshot.child("building").getValue(String.class);

                                                        if(!fridayCourse_name.isEmpty()) {
                                                            tv.setText("Friday: " + fridayCourse_name + ", " + fridayTime + ", " + fridayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Friday: None");

                                                            tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);

                                                        if(fridayBuilding.equals("UV Theater") || fridayBuilding.equals("Olmsted")
                                                                || fridayBuilding.equals("Boyce") || fridayBuilding.equals("Life Sciences")
                                                                || fridayBuilding.equals("Watkins") || fridayBuilding.equals("Sproul") ||
                                                                fridayBuilding.equals("Pierce") || fridayBuilding.equals("Spieth"))
                                                        {
                                                            recommendedLot = "Lot 32";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(fridayBuilding.equals("Hum/SS"))
                                                        {
                                                            recommendedLot = "Lot 30";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(fridayBuilding.equals("Skye") || fridayBuilding.equals("MSE")
                                                                || fridayBuilding.equals("UNLH") || fridayBuilding.equals("CHASS North")
                                                                || fridayBuilding.equals("Bourns") || fridayBuilding.equals("Chung")
                                                                || fridayBuilding.equals("CHASS South") || fridayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 50";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }


                                                    }
                                                    else
                                                    {
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
                                    return;



                                }
                                else if(permitType.equals("Red"))
                                {
                                    Log.d("TAG", "I have a Red permit");
                                    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();

                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("monday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot mondaySnapshot) {
                                                    if(mondaySnapshot.exists()) {

                                                        tv = (TextView) findViewById(R.id.mondaySchedule);

                                                        String monday = mondaySnapshot.getKey();
                                                        String mondayCourse_name = mondaySnapshot.child("course_name").getValue(String.class);
                                                        String mondayTime = mondaySnapshot.child("time").getValue(String.class);
                                                        String mondayBuilding = mondaySnapshot.child("building").getValue(String.class);

                                                        if(!mondayCourse_name.isEmpty()) {
                                                            tv.setText("Monday: " + mondayCourse_name + ", " + mondayTime + ", " + mondayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Monday: None");

                                                            tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);



                                                        if(mondayBuilding.equals("CHASS North") || mondayBuilding.equals("CHASS South")
                                                                || mondayBuilding.equals("Skye") || mondayBuilding.equals("UNLH")
                                                                || mondayBuilding.equals("MSE") || mondayBuilding.equals("Bourns")
                                                                || mondayBuilding.equals("Pierce") || mondayBuilding.equals("Chung")
                                                                || mondayBuilding.equals("Physics") || mondayBuilding.equals("Hum/SS")
                                                                || mondayBuilding.equals("Sproul") || mondayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(mondayBuilding.equals("UV Theater") || mondayBuilding.equals("Olmsted")
                                                                || mondayBuilding.equals("Spieth") || mondayBuilding.equals("Life Sciences")
                                                                || mondayBuilding.equals("Boyce"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }


                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("tuesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot tuesdaySnapshot) {
                                                    if(tuesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.tuesdaySchedule);

                                                        String tuesday = tuesdaySnapshot.getKey();
                                                        String tuesdayCourse_name = tuesdaySnapshot.child("course_name").getValue(String.class);
                                                        String tuesdayTime = tuesdaySnapshot.child("time").getValue(String.class);
                                                        String tuesdayBuilding = tuesdaySnapshot.child("building").getValue(String.class);

                                                        if(!tuesdayCourse_name.isEmpty()) {
                                                            tv.setText("Tuesday: " + tuesdayCourse_name + ", " + tuesdayTime + ", " + tuesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Tuesday: None");

                                                            tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);

                                                        if(tuesdayBuilding.equals("CHASS North") || tuesdayBuilding.equals("CHASS South")
                                                                || tuesdayBuilding.equals("Skye") || tuesdayBuilding.equals("UNLH")
                                                                || tuesdayBuilding.equals("MSE") || tuesdayBuilding.equals("Bourns")
                                                                || tuesdayBuilding.equals("Pierce") || tuesdayBuilding.equals("Chung")
                                                                || tuesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(tuesdayBuilding.equals("UV Theater") || tuesdayBuilding.equals("Olmsted")
                                                                || tuesdayBuilding.equals("Spieth") || tuesdayBuilding.equals("Life Sciences")
                                                                || tuesdayBuilding.equals("Boyce") || tuesdayBuilding.equals("Hum/SS")
                                                                || tuesdayBuilding.equals("Sproul") || tuesdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }


                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("wednesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot wednesdaySnapshot) {
                                                    if(wednesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.wednesdaySchedule);

                                                        String wednesday = wednesdaySnapshot.getKey();
                                                        String wednesdayCourse_name = wednesdaySnapshot.child("course_name").getValue(String.class);
                                                        String wednesdayTime = wednesdaySnapshot.child("time").getValue(String.class);
                                                        String wednesdayBuilding = wednesdaySnapshot.child("building").getValue(String.class);

                                                        if(!wednesdayCourse_name.isEmpty()) {
                                                            tv.setText("Wednesday: " + wednesdayCourse_name + ", " + wednesdayTime + ", " + wednesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Wednesday: None");

                                                            tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);


                                                        if(wednesdayBuilding.equals("CHASS North") || wednesdayBuilding.equals("CHASS South")
                                                                || wednesdayBuilding.equals("Skye") || wednesdayBuilding.equals("UNLH")
                                                                || wednesdayBuilding.equals("MSE") || wednesdayBuilding.equals("Bourns")
                                                                || wednesdayBuilding.equals("Pierce") || wednesdayBuilding.equals("Chung")
                                                                || wednesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(wednesdayBuilding.equals("UV Theater") || wednesdayBuilding.equals("Olmsted")
                                                                || wednesdayBuilding.equals("Spieth") || wednesdayBuilding.equals("Life Sciences")
                                                                || wednesdayBuilding.equals("Boyce") || wednesdayBuilding.equals("Hum/SS")
                                                                || wednesdayBuilding.equals("Sproul") || wednesdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }



                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("thursday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot thursdaySnapshot) {
                                                    if(thursdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.thursdaySchedule);

                                                        String thursday = thursdaySnapshot.getKey();
                                                        String thursdayCourse_name = thursdaySnapshot.child("course_name").getValue(String.class);
                                                        String thursdayTime = thursdaySnapshot.child("time").getValue(String.class);
                                                        String thursdayBuilding = thursdaySnapshot.child("building").getValue(String.class);

                                                        if(!thursdayCourse_name.isEmpty()) {
                                                            tv.setText("Thursday: " + thursdayCourse_name + ", " + thursdayTime + ", " + thursdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Thursday: None" );

                                                            tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);

                                                        if(thursdayBuilding.equals("CHASS North") || thursdayBuilding.equals("CHASS South")
                                                                || thursdayBuilding.equals("Skye") || thursdayBuilding.equals("UNLH")
                                                                || thursdayBuilding.equals("MSE") || thursdayBuilding.equals("Bourns")
                                                                || thursdayBuilding.equals("Pierce") || thursdayBuilding.equals("Chung")
                                                                || thursdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(thursdayBuilding.equals("UV Theater") || thursdayBuilding.equals("Olmsted")
                                                                || thursdayBuilding.equals("Spieth") || thursdayBuilding.equals("Life Sciences")
                                                                || thursdayBuilding.equals("Boyce") || thursdayBuilding.equals("Hum/SS")
                                                                || thursdayBuilding.equals("Sproul") || thursdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }



                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("friday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot fridaySnapshot) {
                                                    if(fridaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.fridaySchedule);

                                                        String friday = fridaySnapshot.getKey();
                                                        String fridayCourse_name = fridaySnapshot.child("course_name").getValue(String.class);
                                                        String fridayTime = fridaySnapshot.child("time").getValue(String.class);
                                                        String fridayBuilding = fridaySnapshot.child("building").getValue(String.class);

                                                        if(!fridayCourse_name.isEmpty()) {
                                                            tv.setText("Friday: " + fridayCourse_name + ", " + fridayTime + ", " + fridayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Friday: None" );

                                                            tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);

                                                        if(fridayBuilding.equals("CHASS North") || fridayBuilding.equals("CHASS South")
                                                                || fridayBuilding.equals("Skye") || fridayBuilding.equals("UNLH")
                                                                || fridayBuilding.equals("MSE") || fridayBuilding.equals("Bourns")
                                                                || fridayBuilding.equals("Pierce") || fridayBuilding.equals("Chung")
                                                                || fridayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(fridayBuilding.equals("UV Theater") || fridayBuilding.equals("Olmsted")
                                                                || fridayBuilding.equals("Spieth") || fridayBuilding.equals("Life Sciences")
                                                                || fridayBuilding.equals("Boyce") || fridayBuilding.equals("Hum/SS")
                                                                || fridayBuilding.equals("Sproul") || fridayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }



                                                    }
                                                    else
                                                    {
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
                                    return;

                                }
                                else if(permitType.equals("Blue"))
                                {
                                        Log.d("TAG", "I have a Blue permit");
                                        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();

                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("monday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot mondaySnapshot) {
                                                    if(mondaySnapshot.exists()) {

                                                        tv = (TextView) findViewById(R.id.mondaySchedule);

                                                        String monday = mondaySnapshot.getKey();
                                                        String mondayCourse_name = mondaySnapshot.child("course_name").getValue(String.class);
                                                        String mondayTime = mondaySnapshot.child("time").getValue(String.class);
                                                        String mondayBuilding = mondaySnapshot.child("building").getValue(String.class);

                                                        if(!mondayCourse_name.isEmpty()) {
                                                            tv.setText("Monday: " + mondayCourse_name + ", " + mondayTime + ", " + mondayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Monday: None");

                                                            tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);

                                                            tv2.setText("Recommended Lot: None" );
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.mondayRecommendedLot);

                                                        if(mondayBuilding.equals("CHASS North") || mondayBuilding.equals("CHASS South")
                                                                || mondayBuilding.equals("Skye") || mondayBuilding.equals("UNLH")
                                                                || mondayBuilding.equals("MSE") || mondayBuilding.equals("Bourns")
                                                                || mondayBuilding.equals("Pierce") || mondayBuilding.equals("Chung")
                                                                || mondayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(mondayBuilding.equals("UV Theater") || mondayBuilding.equals("Olmsted")
                                                                || mondayBuilding.equals("Spieth") || mondayBuilding.equals("Life Sciences")
                                                                || mondayBuilding.equals("Boyce") || mondayBuilding.equals("Hum/SS")
                                                                || mondayBuilding.equals("Sproul") || mondayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                     ;


                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("tuesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot tuesdaySnapshot) {
                                                    if(tuesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.tuesdaySchedule);

                                                        String tuesday = tuesdaySnapshot.getKey();
                                                        String tuesdayCourse_name = tuesdaySnapshot.child("course_name").getValue(String.class);
                                                        String tuesdayTime = tuesdaySnapshot.child("time").getValue(String.class);
                                                        String tuesdayBuilding = tuesdaySnapshot.child("building").getValue(String.class);

                                                        if(!tuesdayCourse_name.isEmpty()) {
                                                            tv.setText("Tuesday: " + tuesdayCourse_name + ", " + tuesdayTime + ", " + tuesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Tuesday: None" );

                                                            tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.tuesdayRecommendedLot);

                                                        if(tuesdayBuilding.equals("CHASS North") || tuesdayBuilding.equals("CHASS South")
                                                                || tuesdayBuilding.equals("Skye") || tuesdayBuilding.equals("UNLH")
                                                                || tuesdayBuilding.equals("MSE") || tuesdayBuilding.equals("Bourns")
                                                                || tuesdayBuilding.equals("Pierce") || tuesdayBuilding.equals("Chung")
                                                                || tuesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(tuesdayBuilding.equals("UV Theater") || tuesdayBuilding.equals("Olmsted")
                                                                || tuesdayBuilding.equals("Spieth") || tuesdayBuilding.equals("Life Sciences")
                                                                || tuesdayBuilding.equals("Boyce") || tuesdayBuilding.equals("Hum/SS")
                                                                || tuesdayBuilding.equals("Sproul") || tuesdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }



                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("wednesday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot wednesdaySnapshot) {
                                                    if(wednesdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.wednesdaySchedule);

                                                        String wednesday = wednesdaySnapshot.getKey();
                                                        String wednesdayCourse_name = wednesdaySnapshot.child("course_name").getValue(String.class);
                                                        String wednesdayTime = wednesdaySnapshot.child("time").getValue(String.class);
                                                        String wednesdayBuilding = wednesdaySnapshot.child("building").getValue(String.class);

                                                        if(!wednesdayCourse_name.isEmpty()) {
                                                            tv.setText("Wednesday: " + wednesdayCourse_name + ", " + wednesdayTime + ", " + wednesdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Wednesday: None");

                                                            tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.wednesdayRecommendedLot);

                                                        if(wednesdayBuilding.equals("CHASS North") || wednesdayBuilding.equals("CHASS South")
                                                                || wednesdayBuilding.equals("Skye") || wednesdayBuilding.equals("UNLH")
                                                                || wednesdayBuilding.equals("MSE") || wednesdayBuilding.equals("Bourns")
                                                                || wednesdayBuilding.equals("Pierce") || wednesdayBuilding.equals("Chung")
                                                                || wednesdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(wednesdayBuilding.equals("UV Theater") || wednesdayBuilding.equals("Olmsted")
                                                                || wednesdayBuilding.equals("Spieth") || wednesdayBuilding.equals("Life Sciences")
                                                                || wednesdayBuilding.equals("Boyce") || wednesdayBuilding.equals("Hum/SS")
                                                                || wednesdayBuilding.equals("Sproul") || wednesdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }




                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("thursday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot thursdaySnapshot) {
                                                    if(thursdaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.thursdaySchedule);

                                                        String thursday = thursdaySnapshot.getKey();
                                                        String thursdayCourse_name = thursdaySnapshot.child("course_name").getValue(String.class);
                                                        String thursdayTime = thursdaySnapshot.child("time").getValue(String.class);
                                                        String thursdayBuilding = thursdaySnapshot.child("building").getValue(String.class);

                                                        if(!thursdayCourse_name.isEmpty()) {
                                                            tv.setText("Thursday: " + thursdayCourse_name + ", " + thursdayTime + ", " + thursdayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Thursday: None");

                                                            tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.thursdayRecommendedLot);

                                                        if(thursdayBuilding.equals("CHASS North") || thursdayBuilding.equals("CHASS South")
                                                                || thursdayBuilding.equals("Skye") || thursdayBuilding.equals("UNLH")
                                                                || thursdayBuilding.equals("MSE") || thursdayBuilding.equals("Bourns")
                                                                || thursdayBuilding.equals("Pierce") || thursdayBuilding.equals("Chung")
                                                                || thursdayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(thursdayBuilding.equals("UV Theater") || thursdayBuilding.equals("Olmsted")
                                                                || thursdayBuilding.equals("Spieth") || thursdayBuilding.equals("Life Sciences")
                                                                || thursdayBuilding.equals("Boyce") || thursdayBuilding.equals("Hum/SS")
                                                                || thursdayBuilding.equals("Sproul") || thursdayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }

                                                    }
                                                    else
                                                    {
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
                                    FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(user2.getUid())
                                            .child("schedule")
                                            .child("friday")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot fridaySnapshot) {
                                                    if(fridaySnapshot.exists())
                                                    {
                                                        tv = (TextView) findViewById(R.id.fridaySchedule);

                                                        String friday = fridaySnapshot.getKey();
                                                        String fridayCourse_name = fridaySnapshot.child("course_name").getValue(String.class);
                                                        String fridayTime = fridaySnapshot.child("time").getValue(String.class);
                                                        String fridayBuilding = fridaySnapshot.child("building").getValue(String.class);

                                                        if(!fridayCourse_name.isEmpty()) {
                                                            tv.setText("Friday: " + fridayCourse_name + ", " + fridayTime + ", " + fridayBuilding);
                                                        }
                                                        else
                                                        {
                                                            tv.setText("Friday: None");

                                                            tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);
                                                            tv2.setText("Recommended Lot: None");
                                                        }

                                                        tv2 = (TextView) findViewById(R.id.fridayRecommendedLot);

                                                        if(fridayBuilding.equals("CHASS North") || fridayBuilding.equals("CHASS South")
                                                                || fridayBuilding.equals("Skye") || fridayBuilding.equals("UNLH")
                                                                || fridayBuilding.equals("MSE") || fridayBuilding.equals("Bourns")
                                                                || fridayBuilding.equals("Pierce") || fridayBuilding.equals("Chung")
                                                                || fridayBuilding.equals("Physics"))
                                                        {
                                                            recommendedLot = "Lot 24";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }
                                                        else if(fridayBuilding.equals("UV Theater") || fridayBuilding.equals("Olmsted")
                                                                || fridayBuilding.equals("Spieth") || fridayBuilding.equals("Life Sciences")
                                                                || fridayBuilding.equals("Boyce") || fridayBuilding.equals("Hum/SS")
                                                                || fridayBuilding.equals("Sproul") || fridayBuilding.equals("Watkins"))
                                                        {
                                                            recommendedLot = "Lot 6";
                                                            tv2.setText("Recommended Lot: " + recommendedLot);
                                                        }

                                                    }
                                                    else
                                                    {
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
                                    return;

                                }
                        }
                        else
                        {
                            Log.d("TAG", "Permit does not work");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "Does not work at all");

                    }
                });


                    }

    }
