package com.evacuationapp.finalevacuationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddReliefGoodsActivity extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference2;
    FirebaseDatabase firebaseDatabase;
    EditText  EdAddReliefGoodsFood, EdAddReliefGoodsFoodPerPerson,EdAddReliefGoodsWater,EdAddReliefGoodsWaterPerPerson,EdAddReliefGoodsSponsor,EdAddReliefGoodsDate,EdAddReliefGoodsMealFor;
    Button btnSave;
    AutoCompleteTextView EdAddReliefGoodsEvacuationName ;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relief_goods);
        firebaseDatabase = FirebaseDatabase.getInstance();
        EdAddReliefGoodsEvacuationName = findViewById(R.id.edAddReliefGoodsEvacuationName);
        EdAddReliefGoodsFood = findViewById(R.id.edAddReliefGoodsFood);
        EdAddReliefGoodsFoodPerPerson = findViewById(R.id.edAddReliefGoodsFoodPerPerson);
        EdAddReliefGoodsWater = findViewById(R.id.edAddReliefGoodsWater);
        EdAddReliefGoodsWaterPerPerson = findViewById(R.id.edAddReliefGoodsWaterPerPerson);
        EdAddReliefGoodsSponsor = findViewById(R.id.edAddReliefGoodsSponsor);
        EdAddReliefGoodsDate = findViewById(R.id.edAddReliefGoodsDate);
        EdAddReliefGoodsMealFor= findViewById(R.id.edAddReliefGoodsMealFor);
        btnSave = findViewById(R.id.btnSave);

        databaseReference2=firebaseDatabase.getReference().child("evacuation");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList array= new ArrayList<>();
                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                    String value2 = String.valueOf(dataSnapshot2.child("evacuationName").getValue());
                    array.add(value2);
                }
                adapterItems = new ArrayAdapter<String>(AddReliefGoodsActivity.this,R.layout.list_item,array);
                EdAddReliefGoodsEvacuationName.setAdapter(adapterItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        EdAddReliefGoodsEvacuationName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(EdAddReliefGoodsEvacuationName.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsFood.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsFoodPerPerson.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsWater.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsWaterPerPerson.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsSponsor.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsDate.getText().toString())
                        && TextUtils.isEmpty(EdAddReliefGoodsMealFor.getText().toString())){
                    Toast.makeText(getApplicationContext()
                            , "Please Input Value", Toast.LENGTH_LONG).show();
                }
                else {
                Places places=new Places();
                //  List<Places> placesList=new ArrayList<>();

                    places.setAddReliefGoodsEvacuationName(EdAddReliefGoodsEvacuationName.getText().toString());
                    places.setAddReliefGoodsFood(EdAddReliefGoodsFood.getText().toString());
                    places.setAddReliefGoodsFoodPerPerson(EdAddReliefGoodsFoodPerPerson.getText().toString());
                    places.setAddReliefGoodsWater(EdAddReliefGoodsWater.getText().toString());
                    places.setAddReliefGoodsWaterPerPerson(EdAddReliefGoodsWaterPerPerson.getText().toString());
                    places.setAddReliefGoodsSponsor(EdAddReliefGoodsSponsor.getText().toString());
                    places.setAddReliefGoodsDate(EdAddReliefGoodsDate.getText().toString());
                    places.setAddReliefGoodsMealFor(EdAddReliefGoodsMealFor.getText().toString());



                databaseReference=firebaseDatabase.getReference().child("reliefgoods");
                databaseReference.push().setValue(places);
                Toast.makeText(getApplicationContext()
                        , "Added Successfully!", Toast.LENGTH_LONG).show();

                EdAddReliefGoodsEvacuationName.setText("");
                EdAddReliefGoodsFood.setText("");
                EdAddReliefGoodsFoodPerPerson.setText("");
                EdAddReliefGoodsWater.setText("");
                EdAddReliefGoodsWaterPerPerson.setText("");
                EdAddReliefGoodsSponsor.setText("");
                EdAddReliefGoodsDate.setText("");
                EdAddReliefGoodsMealFor.setText("");
                startActivity(new Intent(AddReliefGoodsActivity.this , ListAddedReliefGoodsActivity.class));
                }
            }
        });
    }
}