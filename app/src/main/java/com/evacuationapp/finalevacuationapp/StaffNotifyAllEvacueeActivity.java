package com.evacuationapp.finalevacuationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class StaffNotifyAllEvacueeActivity extends AppCompatActivity {
    Button btn_send,btn_sendtoall;
    EditText EvacuationNameED,PhoneNumberED,MessageED;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    String NotificationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_notify_all_evacuee);

        btn_send = findViewById(R.id.bt_send);
        btn_sendtoall = findViewById(R.id.bt_sendtoall);
        EvacuationNameED = findViewById(R.id.evacuationNameED);
        PhoneNumberED = findViewById(R.id.phoneNumberED);
        MessageED = findViewById(R.id.messageED);

        btn_sendtoall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(StaffNotifyAllEvacueeActivity.this
                        , Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){

                    sendMessage();
                }else{
                    ActivityCompat.requestPermissions(StaffNotifyAllEvacueeActivity.this
                            , new String[]{Manifest.permission.SEND_SMS}
                            ,100);
                }

            }
        });
       btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(StaffNotifyAllEvacueeActivity.this
                        , Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){

                    sendMessage1();
                }else{
                    ActivityCompat.requestPermissions(StaffNotifyAllEvacueeActivity.this
                            , new String[]{Manifest.permission.SEND_SMS}
                            ,100);
                }

            }
        });
    }
    private void sendMessage() {
        String sMessage = MessageED.getText().toString().trim();

        Query countQuery5 = databaseReference2.child("evacuee");
        countQuery5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                    String value = String.valueOf(dataSnapshot2.child("contactInfo").getValue());




                    NotificationMessage = ("asdasdsadaasdasdasdasdasd");


                    if (!value.equals("") && !sMessage.equals("")) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(value, null, sMessage, null, null);

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
    private void sendMessage1() {
        String sMessage = MessageED.getText().toString().trim();

        Query countQuery4 = databaseReference2.child("evacuation").child(EvacuationNameED.getText().toString());
        countQuery4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String value = String.valueOf(dataSnapshot1.child("contactInfo").getValue());




                    NotificationMessage = ("asdasdsadaasdasdasdasdasd");


                    if (!value.equals("") && !sMessage.equals("")) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(value, null, sMessage, null, null);

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
}