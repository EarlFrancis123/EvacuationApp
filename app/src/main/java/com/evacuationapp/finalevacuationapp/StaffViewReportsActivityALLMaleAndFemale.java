package com.evacuationapp.finalevacuationapp;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StaffViewReportsActivityALLMaleAndFemale extends AppCompatActivity {
    private PieChart pieChart;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    EditText EvacuationsearchED;
    String sample ;
    Button ButtonSearchBtn;

    String searchtext;
    int count2 = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_reports_allmale_and_female);
        pieChart = findViewById(R.id.activity_main_piechart);
        setupPieChart();


        sample = String.valueOf(EvacuationsearchED);



                ArrayList<Integer> colors = new ArrayList<>();
                ArrayList<PieEntry> entries = new ArrayList<>();

                Query countQuery = databaseReference.child("evacuation");
                countQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {

                            String value2 = String.valueOf(dataSnapshot2.child("evacuationName").getValue());
                            entries.add(new PieEntry(1,value2));

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

                        }




                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });




    }


    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Evacuations");
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