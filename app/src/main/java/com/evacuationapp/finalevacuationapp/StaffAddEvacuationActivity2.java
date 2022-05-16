package com.evacuationapp.finalevacuationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.evacuationapp.finalevacuationapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.evacuationapp.finalevacuationapp.databinding.ActivityStaffaddevacuation2Binding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StaffAddEvacuationActivity2 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private ActivityStaffaddevacuation2Binding binding;
    private Toolbar mainToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivityStaffaddevacuation2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.addevacuation);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Evacuation");


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addevacuation:

                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), StaffHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addtypeofcalamity:
                        startActivity(new Intent(getApplicationContext(), StaffAddCalamityActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addevacuee:
                        startActivity(new Intent(getApplicationContext(), StaffAddEvacueeActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(StaffAddEvacuationActivity2.this, SignInActivity.class));
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(StaffAddEvacuationActivity2.this , SetUpActivity.class));
        }else if(item.getItemId() == R.id.sign_out_menu){
            firebaseAuth.signOut();
            startActivity(new Intent(StaffAddEvacuationActivity2.this , SignInActivity.class));finish();
        }
        else if(item.getItemId() == R.id.reports_menu){
            startActivity(new Intent(StaffAddEvacuationActivity2.this , StaffViewReportsActivity.class));
        }
        else if(item.getItemId() == R.id.notify_menu){
            startActivity(new Intent(StaffAddEvacuationActivity2.this , StaffNotifyAllEvacueeActivity.class));
        }
        return true;
    }
}