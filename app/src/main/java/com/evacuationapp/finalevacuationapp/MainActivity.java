package com.evacuationapp.finalevacuationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button loginasadminbtn,loginasuserbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginasadminbtn = findViewById(R.id.LoginAsAdminBtn);
        loginasuserbtn = findViewById(R.id.LoginAsUserBtn);

        loginasadminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
        loginasuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserSignInActivity.class));
            }
        });
    }
}
