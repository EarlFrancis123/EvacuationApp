package com.evacuationapp.finalevacuationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StaffAddEvacueeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Toolbar mainToolbar;
    EditText EvacueeFnameET, EvacueeLnameET, EvacueeMnameET, EvacueeNumberET, EvacueeGenderET, EvacueeAgeET, EvacueeAddressET, EvacueeBarangayET, EvacueeHeadET, EvacueeCenterET;
    private FirebaseAuth firebaseAuth;
    Button Savebtn;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("evacuee");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_add_evacuee);

        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.addevacuee);
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Evacuation");

        Savebtn = findViewById(R.id.SaveBtn);
        EvacueeFnameET = findViewById(R.id.evacueeFnameET);
        EvacueeLnameET = findViewById(R.id.evacueeLnameET);
        EvacueeMnameET = findViewById(R.id.evacueeMnameET);
        EvacueeNumberET = findViewById(R.id.evacueeNumberET);
        EvacueeGenderET = findViewById(R.id.evacueeGenderET);
        EvacueeAgeET = findViewById(R.id.evacueeAgeET);
        EvacueeAddressET = findViewById(R.id.evacueeAddressET);
        EvacueeBarangayET = findViewById(R.id.evacueeBarangayET);
        EvacueeHeadET = findViewById(R.id.evacueeHeadET);
        EvacueeCenterET = findViewById(R.id.evacueeCenterET);

        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String evacuationcenter = EvacueeCenterET.getText().toString();
                String headoffamily = EvacueeHeadET.getText().toString();
                String barangay = EvacueeBarangayET.getText().toString();
                String address = EvacueeAddressET.getText().toString();
                String fname = EvacueeFnameET.getText().toString();
                String lname = EvacueeLnameET.getText().toString();
                String mname = EvacueeMnameET.getText().toString();
                String number = EvacueeNumberET.getText().toString();
                String gender = EvacueeGenderET.getText().toString();
                String age = EvacueeAgeET.getText().toString();
                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("evacuationcenter", evacuationcenter);
                userMap.put("headoffamily", headoffamily);
                userMap.put("barangay", barangay);
                userMap.put("address", address);
                userMap.put("age", age);
                userMap.put("gender", gender);
                userMap.put("number", number);
                userMap.put("middlename", mname);
                userMap.put("lastname", lname);
                userMap.put("firstname", fname);

                root.push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StaffAddEvacueeActivity.this, "Calamity Details Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StaffAddEvacueeActivity.this, "Error! Calamity Details Added" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addtypeofcalamity:
                        startActivity(new Intent(getApplicationContext(), StaffAddCalamityActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), StaffHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addevacuation:
                        startActivity(new Intent(getApplicationContext(), StaffAddEvacuationActivity2.class));
                        overridePendingTransition(0, 0);

                    case R.id.addevacuee:

                        return true;


                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_menu) {
            startActivity(new Intent(StaffAddEvacueeActivity.this, SetUpActivity.class));
        } else if (item.getItemId() == R.id.sign_out_menu) {
            firebaseAuth.signOut();
            startActivity(new Intent(StaffAddEvacueeActivity.this, SignInActivity.class));
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(StaffAddEvacueeActivity.this, SignInActivity.class));
            finish();
        }
    }
}