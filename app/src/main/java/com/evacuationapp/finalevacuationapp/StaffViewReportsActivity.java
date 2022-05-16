package com.evacuationapp.finalevacuationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StaffViewReportsActivity extends AppCompatActivity {
CardView GenderCV, EvacuationCV,CalamityCV,AllReportCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_reports);

        GenderCV = findViewById(R.id.genderCV);
        EvacuationCV = findViewById(R.id.evacuationCV);
        CalamityCV = findViewById(R.id.calamityCV);
        AllReportCV = findViewById(R.id.allReportCV);

        GenderCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffViewReportsActivity.this , StaffViewReportsActivityMaleAndFemale.class));
            }
        });
        AllReportCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffViewReportsActivity.this , StaffViewReportsActivityGeneralReports.class));
            }
        });
        EvacuationCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffViewReportsActivity.this , StaffViewReportsActivityEvacuation.class));
            }
        });
    }
}