package com.evacuationapp.finalevacuationapp;

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
import com.google.protobuf.StringValue;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StaffViewReportsActivityALLEvacuation extends AppCompatActivity {
    private PieChart pieChart;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    String sample ;
    Button ButtonSearchBtn;
    TextView MaleTV, FemaleTV;
    String searchtext;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;
    AutoCompleteTextView EvacuationsearchED;
    ArrayAdapter<String> adapterItems;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_reports_allevacuation);
        pieChart = findViewById(R.id.activity_main_piechart1);
        setupPieChart();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        EvacuationsearchED = findViewById(R.id.auto_complete_txt_evacuationName2);
        ButtonSearchBtn = findViewById(R.id.buttonSearchBtn);

        MaleTV = findViewById(R.id.maleTV);
        FemaleTV = findViewById(R.id.femaleTV);

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
                    adapterItems = new ArrayAdapter<String>(StaffViewReportsActivityALLEvacuation.this.getApplicationContext(), R.layout.list_item, array);
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
            Toast.makeText(StaffViewReportsActivityALLEvacuation.this, String.valueOf(e), Toast.LENGTH_SHORT).show();

        }







        ButtonSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> colors = new ArrayList<>();
                ArrayList<PieEntry> entries = new ArrayList<>();
                Query countQuery = databaseReference.child("Capacity").child(EvacuationsearchED.getText().toString());
                countQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        int count,count2 ;

                                String value2 = String.valueOf(snapshot.child("evacuationCapacity").getValue());
                        String value3 = String.valueOf(snapshot.child("totalEvacuee").getValue());
                                count = Integer.parseInt(value2);
                        count2 = Integer.parseInt(value3);
                        entries.add(new PieEntry(count,"Vacant"));
                        entries.add(new PieEntry(count2,"Occupied"));
                        MaleTV.setText(String.valueOf(count));
                        FemaleTV.setText(String.valueOf(count2));
                        for (int color: ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }
                        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                            colors.add(color);
                        }
                        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
                        dataSet.setColors(colors);

                        PieData data = new PieData(dataSet);
                        data.setDrawValues(true);
                        data.setValueFormatter(new PercentFormatter(pieChart));
                        data.setValueTextSize(12f);
                        data.setValueTextColor(Color.BLACK);

                        pieChart.setData(data);
                        pieChart.invalidate();
                        pieChart.animateY(1400, Easing.EaseInOutQuad);


                        Toast.makeText(StaffViewReportsActivityALLEvacuation.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
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
        pieChart.setCenterText("Genders");
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
