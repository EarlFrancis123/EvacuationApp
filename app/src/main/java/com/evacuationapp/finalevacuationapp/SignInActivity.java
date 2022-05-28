package com.evacuationapp.finalevacuationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    private EditText signInEmail , signInPass;
    private Button signInBtn;
    private TextView sign_up_text;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    TextView EForgotPassword;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        signInEmail = findViewById(R.id.sign_in_email);
        signInPass = findViewById(R.id.sing_in_pass);
        signInBtn = findViewById(R.id.sign_in_btn);
        sign_up_text = findViewById(R.id.sign_up_text);
        EForgotPassword = findViewById(R.id.forgotPasswordTV);

        firestore = FirebaseFirestore.getInstance();
        sign_up_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this , SignUpActivity.class));
            }
        });



        signInBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString();
                String pass = signInPass.getText().toString();
                databaseReference2=firebaseDatabase2.getReference().child("Staff");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean check = false;
                        for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {

                            String value1 = String.valueOf(dataSnapshot2.child("email").getValue());
                            String value2 = String.valueOf(dataSnapshot2.child("password").getValue());
                            if (value1.equals(signInEmail.getText().toString()) && value2.equals(signInPass.getText().toString())) {
                                check = true;

                            }

                        }
                        if (check == true) {
                            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignInActivity.this, "Login Successfull !!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInActivity.this, StaffHomeActivity.class));
                                    finish();
                                }
                            });
                        }else{
                            Toast.makeText(SignInActivity.this, "User Not Registered!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
              /**  if (TextUtils.isEmpty(email)) {
                    signInEmail.setError("Email is Required");
                    return;
                }
                else if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    signInEmail.setError("Incorrect Email Format");
                }
                else if (TextUtils.isEmpty(pass)) {
                    signInPass.setError("Password is Required");
                    return;
                }
                else {



                }**/

            }
        });
        EForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your Email to Recieved the Reset link");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignInActivity.this, "Rest link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Error! Reset link is not sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

    }
}

