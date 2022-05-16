package com.evacuationapp.finalevacuationapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRescueActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private Toolbar mainToolbar;
    private FirebaseFirestore firestore;
    EditText etPhone,etMessage;
    Button RescueBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    String NotificationMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rescue);
        RescueBtn = findViewById(R.id.rescueBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mainToolbar = findViewById(R.id.main_toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.rescue);
        RescueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(UserRescueActivity.this
                        , Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){

                    sendMessage();
                }else{
                    ActivityCompat.requestPermissions(UserRescueActivity.this
                            , new String[]{Manifest.permission.SEND_SMS}
                            ,100);
                }

            }
        });



                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.userlandingpage:
                                startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
                                overridePendingTransition(0, 0);
                                return true;

                            case R.id.rescue:
                                return true;

                            case R.id.searchevacuee:
                                startActivity(new Intent(getApplicationContext(), UserSearchEvacueeActivity.class));
                                overridePendingTransition(0, 0);
                                return true;

                            case R.id.nearest:
                                startActivity(new Intent(getApplicationContext(), UserNearestEvacuationActivity2.class));
                                overridePendingTransition(0, 0);
                                return true;


                        }
                        return false;
                    }
                });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED){


        }else{
            Toast.makeText(getApplicationContext()
                    ,"Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(UserRescueActivity.this, SignInActivity.class));
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
            startActivity(new Intent(UserRescueActivity.this , UserSetUpActivity.class));
        }else if(item.getItemId() == R.id.sign_out_menu){
            firebaseAuth.signOut();
            startActivity(new Intent(UserRescueActivity.this , UserSignInActivity.class));
            finish();
        }
        return true;
    }
    private void sendMessage() {
        Query countQuery2 = databaseReference.child("evacuation");
        countQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String value = String.valueOf(dataSnapshot1.child("evacuationNumber").getValue());


                    NotificationMessage = ("Need Urget Help!!!!!!!!!" + "/n" + "My Location = ako pani e butang nga automatic kuha sa location so ILOVEYOU LOVELEY SA DIRI");


                    if (!value.equals("") && !NotificationMessage.equals("")) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(value, null, NotificationMessage, null, null);

                        Toast.makeText(getApplicationContext()
                                , "SMS sent successfully!", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext()
                                , "Enter value First.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
