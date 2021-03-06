package com.example.parking;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private GoogleSignInAccount account;
    private GoogleSignInClient signInClient;
    private boolean notificationsOn;
    private int notificationsHours;

    public static TextView textView_big_springs;
    public static TextView textView_lot_6;
    public static TextView textView_lot_24;
    public static TextView textView_lot_26;
    public static TextView textView_lot_30;
    public static TextView textView_lot_32;
    public static TextView textView_lot_50;

    // notification variables
    Handler mHandler;

    private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;
    String day;
    String time;
    final DateFormat df = new SimpleDateFormat("HH:mm:ss");

    String userTime = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(HomePage.this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(HomePage.this, gso);
        this.mHandler = new Handler();
        m_Runnable.run();   // updates system time for notification

        // Drawer
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // Handle drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // Handle drawer buttons
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase authentication
        textView_big_springs = (TextView) findViewById(R.id.textView_springs);
        textView_lot_6 = (TextView) findViewById(R.id.textView_lot_6);
        textView_lot_24 = (TextView) findViewById(R.id.textView_lot_24);
        textView_lot_26 = (TextView) findViewById(R.id.textView_lot_26);
        textView_lot_30 = (TextView) findViewById(R.id.textView_lot_30);
        textView_lot_32 = (TextView) findViewById(R.id.textView_lot_32);
        textView_lot_50 = (TextView) findViewById(R.id.textView_lot_50);

        setupbuttons();

        Calendar calendar = Calendar.getInstance();     // Notifications
        day = LocalDate.now().getDayOfWeek().name();    // Notifications
        time = df.format(Calendar.getInstance().getTime());     // Notifications

    }


    // Notifications
    public void sendOnChannel1() {
        String reminder = "Get your ass to class " + day + " " + time +"\n" ;
        String lots = "Big Springs: " + textView_big_springs.getText().toString()
                + "\nLot 6: " + textView_lot_6.getText().toString()
                + "\nLot 24: " + textView_lot_24.getText().toString()
                + "\nLot 26: " + textView_lot_26.getText().toString()
                + "\nLot 30: " + textView_lot_30.getText().toString()
                + "\nLot 32: " + textView_lot_32.getText().toString()
                + "\nLot 50: " + textView_lot_50.getText().toString();

        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent startApp = PendingIntent.getActivity(this, 0,
                intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_local_parking_black_24dp);
        builder.setContentTitle(reminder);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(lots));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(startApp);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    //Notifications
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "Inculde all notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    private void setupbuttons(){
        Button bigsp = findViewById(R.id.button1);
        Button lot6 = findViewById(R.id.button2);
        Button lot24 = findViewById(R.id.button3);
        Button lot26 = findViewById(R.id.button4);
        Button lot30 = findViewById(R.id.button5);
        Button lot32 = findViewById(R.id.button6);
        Button lot50 = findViewById(R.id.button7);

        bigsp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Big Springs"); //Optional parameters
                myIntent.putExtra("builds", "Orbach, Physics, Chung, Bourns, Boyce, Webber"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.9753,-117.320114" + " (Big Springs)" );
                myIntent.putExtra("time", "7 min");
                HomePage.this.startActivity(myIntent);

            }
        });

        textView_big_springs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Big Springs"); //Optional parameters
                myIntent.putExtra("builds", "Orbach, Physics, Chung, Bourns, Boyce, Webber"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.9753,-117.320114" + " (Big Springs)" );
                myIntent.putExtra("time", "7 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 6"); //Optional parameters
                myIntent.putExtra("builds", "Psychology, Olmsted, Humanities, Watkins, Sproul"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.969791,-117.327579" + " (Lot 6)" );
                myIntent.putExtra("time", "4 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        textView_lot_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 6"); //Optional parameters
                myIntent.putExtra("builds", "Psychology, Olmsted, Humanities, Watkins, Sproul"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.969791,-117.327579" + " (Lot 6)" );
                myIntent.putExtra("time", "4 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot24.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 24"); //Optional parameters
                myIntent.putExtra("builds", "MSE, UNLH, Skye, CHASS, Bourns, Chung"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.978020,-117.330716" + " (Lot  24)" );
                myIntent.putExtra("time", "6 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        textView_lot_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 24"); //Optional parameters
                myIntent.putExtra("builds", "MSE, UNLH, Skye, CHASS, Bourns, Chung"); //Optional parameters
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.978020,-117.330716" + " (Lot  24)" );
                myIntent.putExtra("time", "6 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot26.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 26"); //Optional parameters
                myIntent.putExtra("builds", "MSE, UNLH, Skye, CHASS, Bourns, Chung");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.981198,-117.334054" + " (Lot 26)" );
                myIntent.putExtra("time", "18 min");
                HomePage.this.startActivity(myIntent);

            }
        });
        textView_lot_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 26"); //Optional parameters
                myIntent.putExtra("builds", "MSE, UNLH, Skye, CHASS, Bourns, Chung");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.981198,-117.334054" + " (Lot 26)" );
                myIntent.putExtra("time", "18 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot30.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 30"); //Optional parameters
                myIntent.putExtra("builds", "Olmsted, Humanities, Watkins, H&SS");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.970508,-117.331924" + " (Lot 30)" );
                myIntent.putExtra("time", "10 min");
                HomePage.this.startActivity(myIntent);

            }
        });
        textView_lot_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 30"); //Optional parameters
                myIntent.putExtra("builds", "Olmsted, Humanities, Watkins, H&SS");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.970508,-117.331924" + " (Lot 30)" );
                myIntent.putExtra("time", "10 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot32.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 32"); //Optional parameters
                myIntent.putExtra("builds", "Olmsted, Humanities, Watkins, H&SS");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.968962,-117.330248" + " (Lot 32)" );
                myIntent.putExtra("time", "11 min");
                HomePage.this.startActivity(myIntent);

            }
        });
        textView_lot_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 32"); //Optional parameters
                myIntent.putExtra("builds", "Olmsted, Humanities, Watkins, H&SS");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.968962,-117.330248" + " (Lot 32)" );
                myIntent.putExtra("time", "11 min");
                HomePage.this.startActivity(myIntent);
            }
        });
        lot50.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 50"); //Optional parameters
                myIntent.putExtra("builds", "Arts, CHASS, UNLH, H&SS, Sproul");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.974956,-117.336570" + " (Lot 50)" );
                myIntent.putExtra("time", "14 min");
                HomePage.this.startActivity(myIntent);

            }
        });
        textView_lot_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, LotInfo.class);
                myIntent.putExtra("name", "Lot 50"); //Optional parameters
                myIntent.putExtra("builds", "Arts, CHASS, UNLH, H&SS, Sproul");
                myIntent.putExtra("maps", "http://maps.google.com/maps?q=loc:33.974956,-117.336570" + " (Lot 50)" );
                myIntent.putExtra("time", "14 min");
                HomePage.this.startActivity(myIntent);
            }
        });

        return;
    }

    // updates notification time and parking spaces
    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            time = df.format(Calendar.getInstance().getTime());
            getNotificationSwitchVal();
            getNotifcationHours();
            if(!userTime.isEmpty() && time.equals(userTime)) {
                if (notificationsOn) {
                    sendOnChannel1();
                }
            }


            com.example.parking.fetchData process = new com.example.parking.fetchData();
            process.execute();
            HomePage.this.mHandler.postDelayed(m_Runnable,1000);
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        // In case user makes it here without authorized account
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        //handle the already login user
        // Only welcome user the first time app is started
        String welcome_msg = "";
        if (mAuth.getCurrentUser() != null) {
            if (!((AppCtx) getApplicationContext()).user_welcomed) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                WelcomeUser(user.getUid());
            }
        } else if (account != null) {
            if (!((AppCtx) getApplicationContext()).user_welcomed) {
                Toast.makeText(getApplicationContext(), "Logged in as " + account.getDisplayName(),
                        Toast.LENGTH_LONG).show();
                ((AppCtx)getApplicationContext()).user_welcomed = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Stop app from closing when back button is pressed since MainActivity is 'finish()'ed
        // So only close drawer is available
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(HomePage.this, Profile.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(HomePage.this, Dashboard.class));
                break;
            case R.id.nav_recommendations:
                Intent intent = new Intent(HomePage.this, Recommendations.class);
                Bundle extras = new Bundle();
                extras.putString("big_springs", textView_big_springs.getText().toString());
                extras.putString("lot_6", textView_lot_6.getText().toString());
                extras.putString("lot_24", textView_lot_24.getText().toString());
                extras.putString("lot_26", textView_lot_26.getText().toString());
                extras.putString("lot_30", textView_lot_30.getText().toString());
                extras.putString("lot_32", textView_lot_32.getText().toString());
                extras.putString("lot_50", textView_lot_50.getText().toString());
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.nav_notifications:
                startActivity(new Intent(HomePage.this, Notification.class));
                break;
            case R.id.nav_logout:
                ((AppCtx)getApplicationContext()).user_welcomed = false;
                if (mAuth.getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    if (signInClient != null)
                        signInClient.signOut();
                    }
                Toast.makeText(HomePage.this, "Sign out successful",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void WelcomeUser(String uid) {
        FirebaseDatabase.getInstance()                // Get everything
                .getReference("Users")           // From that get Users
                .child(uid)                           // From that get specific uid (class)
                .child("name")                        // From that get that class field "name"
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Logged in as " + dataSnapshot.getValue(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        // Don't welcome user again
        ((AppCtx)getApplicationContext()).user_welcomed = true;
    }

    private void getNotificationSwitchVal() {
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(user.getUid())
                    .child("notifications")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                notificationsOn = ((String) dataSnapshot.getValue()).matches("ON");
                            } else {
                                Log.e("Homepage", "getNoficationSwitchVal: Error: notifications val missing!");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(HomePage.this, databaseError.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    // Currently only handles current day.
    private void getNotifcationHours() {
        if (mAuth.getCurrentUser() != null) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String hoursBefore = dataSnapshot.child("notificationHours").getValue(String.class);
                                if (hoursBefore != null) {
                                    notificationsHours = Integer.parseInt(hoursBefore);

                                    // Get today's day of the week
                                    SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
                                    String day = formatter.format(Calendar.getInstance().getTime()).toLowerCase();

                                   // Get time of today's alarm
                                   String databaseTime = dataSnapshot.child("schedule").child(day).child("time").getValue(String.class);
                                   updateUserTime(Integer.parseInt(hoursBefore), databaseTime);
                                }
                            } else {
                                Log.e("Homepage", "getNoficationHours: Error: User doesn't exist" );
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(HomePage.this, databaseError.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public void updateUserTime(int hoursBefore, String databaseTime) {
        if (mAuth.getCurrentUser() != null) {
            if (databaseTime != null && !databaseTime.isEmpty()) {
                String[] times = databaseTime.split(":");
                boolean pm = times[1].substring(2).trim().matches("PM");
                int hours = Integer.parseInt(times[0].trim()) + (pm ? 12 : 0) - hoursBefore;
                int mins = Integer.parseInt(times[1].substring(0, 2));
                String minutes = String.valueOf(mins);
                if (mins < 10) {
                    minutes = "0" + mins;
                }
                userTime = hours + ":" + minutes + ":" + "00";

            }
        }
    }
}
