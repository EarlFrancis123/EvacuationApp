package com.evacuationapp.finalevacuationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserNearestEvacuationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private Toolbar mainToolbar;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_nearest_evacuation);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mainToolbar = findViewById(R.id.main_toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.nearest);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.userlandingpage:
                        startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nearest:
                        return true;

                    case R.id.rescue:
                        startActivity(new Intent(getApplicationContext(),UserRescueActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.searchevacuee:
                        startActivity(new Intent(getApplicationContext(),UserSearchEvacueeActivity.class));
                        overridePendingTransition(0,0);
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
            startActivity(new Intent(UserNearestEvacuationActivity.this, SignInActivity.class));
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
            startActivity(new Intent(UserNearestEvacuationActivity.this , UserSetUpActivity.class));
        }else if(item.getItemId() == R.id.sign_out_menu){
            firebaseAuth.signOut();
            startActivity(new Intent(UserNearestEvacuationActivity.this , UserSignInActivity.class));
            finish();
        }
        return true;
    }
}