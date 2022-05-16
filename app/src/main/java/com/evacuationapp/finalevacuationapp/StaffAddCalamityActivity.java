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

public class StaffAddCalamityActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button ViewCalamityBtn;
    private Toolbar mainToolbar;
    private FirebaseAuth firebaseAuth;
    Button Savebtn;
    EditText CalamityName, CalamityDetails;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("calamity");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_add_calamity);

        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.addtypeofcalamity);
        ViewCalamityBtn = findViewById(R.id.viewCalamityBtn);
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Evacuation");

        Savebtn = findViewById(R.id.SaveBtn);
        CalamityName = findViewById(R.id.textInputET);
        CalamityDetails = findViewById(R.id.textInputDetailsET);

        ViewCalamityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffAddCalamityActivity.this, StaffViewCalamityAvtivity.class));
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addtypeofcalamity:

                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), StaffHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addevacuation:
                        startActivity(new Intent(getApplicationContext(), StaffAddEvacuationActivity2.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addevacuee:
                        startActivity(new Intent(getApplicationContext(), StaffAddEvacueeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = CalamityName.getText().toString();
                String details = CalamityDetails.getText().toString();

                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("name", name);
                userMap.put("details", details);


                root.push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StaffAddCalamityActivity.this, "Calamity Details Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StaffAddCalamityActivity.this, "Error! Calamity Details Added" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
            startActivity(new Intent(StaffAddCalamityActivity.this, SetUpActivity.class));
        } else if (item.getItemId() == R.id.sign_out_menu) {
            firebaseAuth.signOut();
            startActivity(new Intent(StaffAddCalamityActivity.this, SignInActivity.class));
            finish();
        }
        return true;


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if(currentUser == null){
                startActivity(new Intent(StaffAddCalamityActivity.this, SignInActivity.class));
                finish();
            }


}

}

