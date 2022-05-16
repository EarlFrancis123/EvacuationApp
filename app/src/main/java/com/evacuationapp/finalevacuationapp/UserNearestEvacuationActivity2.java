package com.evacuationapp.finalevacuationapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evacuationapp.finalevacuationapp.ui.main.UserNearestSectionsPagerAdapter;
import com.evacuationapp.finalevacuationapp.databinding.ActivityUserNearestEvacuation2Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserNearestEvacuationActivity2 extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Toolbar mainToolbar;
    private ActivityUserNearestEvacuation2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserNearestEvacuation2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserNearestSectionsPagerAdapter sectionsPagerAdapter = new UserNearestSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Evacuation");
        firebaseAuth = FirebaseAuth.getInstance();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(UserNearestEvacuationActivity2.this, UserSignInActivity.class));
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2 , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(UserNearestEvacuationActivity2.this , UserSetUpActivity.class));
        }else if(item.getItemId() == R.id.sign_out_menu){
            firebaseAuth.signOut();
            startActivity(new Intent(UserNearestEvacuationActivity2.this , UserSignInActivity.class));
            finish();
        }
        return true;
    }
}