package com.example.studyplanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.studyplanner.ui.main.SectionsPagerAdapter;
import com.example.studyplanner.databinding.ActivityTabbedPlannerBinding;

import java.util.Objects;

public class TabbedPlanner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityTabbedPlannerBinding binding;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar customActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("onCreate_htg");

        binding = ActivityTabbedPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabbedPlanner.this, AddingEvents.class);
                startActivity(intent);
            }
        });

        customActionBar = findViewById(R.id.action_bar_id);
        customActionBar.setTitle("Events");
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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("htg_sensei");
        Intent intent = null;
        switch(item.getItemId())
        {
            case R.id.nav_home:
                intent = new Intent (this, MainActivity.class);
                intent.putExtra("Fragment", "Home");
                startActivity(intent);
                finish();
                break;

            case R.id.nav_events:
                break;

            case R.id.nav_calender:
                intent = new Intent (this, MainActivity.class);
                intent.putExtra("Fragment", "Calender");
                startActivity(intent);
                finish();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}