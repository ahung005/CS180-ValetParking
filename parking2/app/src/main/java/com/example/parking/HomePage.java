package com.example.parking;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    public static TextView textView_big_springs;
    public static TextView textView_lot_6;
    public static TextView textView_lot_24;
    public static TextView textView_lot_26;
    public static TextView textView_lot_30;
    public static TextView textView_lot_32;
    public static TextView textView_lot_50;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.mHandler = new Handler();
        m_Runnable.run();

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
        mAuth = FirebaseAuth.getInstance();

        textView_big_springs = (TextView) findViewById(R.id.textView_springs);
        textView_lot_6 = (TextView) findViewById(R.id.textView_lot_6);
        textView_lot_24 = (TextView) findViewById(R.id.textView_lot_24);
        textView_lot_26 = (TextView) findViewById(R.id.textView_lot_26);
        textView_lot_30 = (TextView) findViewById(R.id.textView_lot_30);
        textView_lot_32 = (TextView) findViewById(R.id.textView_lot_32);
        textView_lot_50 = (TextView) findViewById(R.id.textView_lot_50);

    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            com.example.test.fetchData process = new com.example.test.fetchData();
            process.execute();
            HomePage.this.mHandler.postDelayed(m_Runnable,60000);
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        // In case user makes it here without authorized account
        if (mAuth.getCurrentUser() == null) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        //handle the already login user
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
                startActivity(new Intent(getApplicationContext(), Profile.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomePage.this, "Sign out successful",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
