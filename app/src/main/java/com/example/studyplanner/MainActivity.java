package com.example.studyplanner;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar customActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set customized action bar
        customActionBar = findViewById(R.id.action_bar_id);
        customActionBar.setTitle("Home");
        setSupportActionBar(customActionBar);


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        //onclick events in navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // to make the Navigation drawer icon always appear on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent!=null)
        {
            if (intent.getStringExtra("Fragment") != null) {
                if (intent.getStringExtra("Fragment").equals("Calender")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new CalenderFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_calender);
                    customActionBar.setTitle("Calender");
                } else if (intent.getStringExtra("Fragment").equals("Home")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            new HomeFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                    customActionBar.setTitle("Home");
                }
            }
            else
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }


//        if(savedInstanceState==null)
//        {
//
//        }

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();
                customActionBar.setTitle("Home");
                break;

            case R.id.nav_events:
                Intent intent = new Intent(this, TabbedPlanner.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_calender:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new CalenderFragment()).commit();
                customActionBar.setTitle("Calender");
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}