package com.evacuationapp.finalevacuationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffViewReportsActivityGeneralReports extends AppCompatActivity {

    private BarChart chart;
    float barWidth;
    float barSpace;
    float groupSpace;

    Places lt = new Places();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_reports_general_reports);

        Query countQuery = databaseReference.child("evacuation");
        countQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
                public void onDataChange(DataSnapshot snapshot) {


                     int  count = (int) snapshot.getChildrenCount();

                }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });



        barSpace = 0f;
        groupSpace = 0.4f;

        chart = (BarChart)findViewById(R.id.barchart);
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        int groupCount = 6;

        ArrayList xVals = new ArrayList();

        xVals.add("Total Evacuation");
        xVals.add("Total Calamity");
        xVals.add("Total Evacuee");


        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        yVals1.add(new BarEntry(1, lt.getTotalevacuation()));
        yVals2.add(new BarEntry(1,  lt.getTotalevacuation()));
        yVals1.add(new BarEntry(0,  0));
        yVals2.add(new BarEntry(0,  0));
        yVals1.add(new BarEntry(0,  0));
        yVals2.add(new BarEntry(0,  0));



        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "A");
        set1.setColor(Color.RED);
        set2 = new BarDataSet(yVals2, "B");
        set2.setColor(Color.RED);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

    }
}