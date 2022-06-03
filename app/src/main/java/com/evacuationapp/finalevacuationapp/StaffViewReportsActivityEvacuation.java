package com.evacuationapp.finalevacuationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;

public class StaffViewReportsActivityEvacuation extends AppCompatActivity {
    private PieChart pieChart;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    String sample ;
    TextView MinorTV, SeniorTV,AdultsTV, DisabilityTV;

    Button ButtonSearchBtn;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;
    AutoCompleteTextView EvacuationsearchED;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_reports_evacuation);
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();
        EvacuationsearchED = findViewById(R.id.auto_complete_txt_evacuationName);
        ButtonSearchBtn = findViewById(R.id.buttonSearchBtn);
        sample = String.valueOf(EvacuationsearchED);
        MinorTV = findViewById(R.id.minorTV);
        SeniorTV = findViewById(R.id.seniorTV);
        DisabilityTV = findViewById(R.id.disabilityTV);
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        AdultsTV = findViewById(R.id.adultsTV);

        try {
            databaseReference2=firebaseDatabase2.getReference().child("evacuation");
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList array = new ArrayList<>();
                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                        String value2 = String.valueOf(dataSnapshot2.child("evacuationName").getValue());
                        array.add(value2);
                    }
                    adapterItems = new ArrayAdapter<String>(StaffViewReportsActivityEvacuation.this.getApplicationContext(), R.layout.list_item, array);
                    EvacuationsearchED.setAdapter(adapterItems);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            EvacuationsearchED.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                }
            });
        }catch (Exception e){
            Toast.makeText(StaffViewReportsActivityEvacuation.this, String.valueOf(e), Toast.LENGTH_SHORT).show();

        }



        ButtonSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> colors = new ArrayList<>();
                ArrayList<PieEntry> entries = new ArrayList<>();

                Query countQuery = databaseReference.child("evacuee");
                countQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                            String value = String.valueOf(dataSnapshot2.child("evacuationName").getValue());

                            if(value.equals( EvacuationsearchED.getText().toString())){
                                String value2 = String.valueOf(dataSnapshot2.child("ageautocomplete").getValue());
                                if(value2.equals("Minor")){
                                    count++;
                                }
                            }
                            else {
                                Toast.makeText(StaffViewReportsActivityEvacuation.this, "No Record", Toast.LENGTH_SHORT).show();
                            }

                        }
                        entries.add(new PieEntry(count,"Minor"));
                        MinorTV.setText(String.valueOf(count));
                        for (int color: ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }
                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                        }
                        PieDataSet dataSet = new PieDataSet(entries, "Evacuation");
                        dataSet.setColors(colors);

                        PieData data = new PieData(dataSet);
                        data.setDrawValues(true);
                        data.setValueFormatter(new PercentFormatter(pieChart));
                        data.setValueTextSize(12f);
                        data.setValueTextColor(Color.BLACK);

                        pieChart.setData(data);
                        pieChart.invalidate();

                        pieChart.animateY(1400, Easing.EaseInOutQuad);



                        Toast.makeText(StaffViewReportsActivityEvacuation.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
                Query countQuery2 = databaseReference.child("evacuee");
                countQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                            String value = String.valueOf(dataSnapshot2.child("evacuationName").getValue());

                            if(value.equals( EvacuationsearchED.getText().toString())){
                                String value2 = String.valueOf(dataSnapshot2.child("ageautocomplete").getValue());
                                if(value2.equals("Adult")){
                                    count++;
                                }
                            }
                            else {
                                Toast.makeText(StaffViewReportsActivityEvacuation.this, "No Record", Toast.LENGTH_SHORT).show();
                            }

                        }
                        entries.add(new PieEntry(count,"Adult"));
                        AdultsTV.setText(String.valueOf(count));
                        for (int color: ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }
                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                            PieDataSet dataSet = new PieDataSet(entries, "Evacuation");
                            dataSet.setColors(colors);

                            PieData data = new PieData(dataSet);
                            data.setDrawValues(true);
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                            pieChart.invalidate();

                            pieChart.animateY(1400, Easing.EaseInOutQuad);
                        }
                    }
                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
                Query countQuery3 = databaseReference.child("evacuee");
                countQuery3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                            String value = String.valueOf(dataSnapshot2.child("evacuationName").getValue());

                            if(value.equals( EvacuationsearchED.getText().toString())){
                                String value2 = String.valueOf(dataSnapshot2.child("ageautocomplete").getValue());
                                if(value2.equals("Senior Citizen")){
                                    count++;
                                }
                            }
                            else {
                                Toast.makeText(StaffViewReportsActivityEvacuation.this, "No Record", Toast.LENGTH_SHORT).show();
                            }

                        }
                        entries.add(new PieEntry(count,"Senior Citizen"));
                        SeniorTV.setText(String.valueOf(count));
                        for (int color: ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }
                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                            PieDataSet dataSet = new PieDataSet(entries, "Evacuation");
                            dataSet.setColors(colors);

                            PieData data = new PieData(dataSet);
                            data.setDrawValues(true);
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                            pieChart.invalidate();

                            pieChart.animateY(1400, Easing.EaseInOutQuad);



                        }

                    }
                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
                Query countQuery4 = databaseReference.child("evacuee");
                countQuery4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int count = 0;
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                            String value = String.valueOf(dataSnapshot2.child("evacuationName").getValue());

                            if(value.equals( EvacuationsearchED.getText().toString())){
                                String value2 = String.valueOf(dataSnapshot2.child("disability").getValue());
                                if(value2.equals("Have")){
                                    count++;
                                }
                            }
                            else {
                                Toast.makeText(StaffViewReportsActivityEvacuation.this, "No Record", Toast.LENGTH_SHORT).show();
                            }

                        }
                        entries.add(new PieEntry(count,"Disability"));
                        DisabilityTV.setText(String.valueOf(count));
                        for (int color: ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }
                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                            PieDataSet dataSet = new PieDataSet(entries, "Evacuation");
                            dataSet.setColors(colors);

                            PieData data = new PieData(dataSet);
                            data.setDrawValues(true);
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                            pieChart.invalidate();

                            pieChart.animateY(1400, Easing.EaseInOutQuad);



                        }

                    }
                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
            }
        });
    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Evacuation");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }
}