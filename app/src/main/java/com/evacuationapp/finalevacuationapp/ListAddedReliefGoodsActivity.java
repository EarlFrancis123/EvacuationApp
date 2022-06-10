package com.evacuationapp.finalevacuationapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evacuationapp.finalevacuationapp.Adapter.myadapter;
import com.evacuationapp.finalevacuationapp.Adapter.myadapter2;
import com.evacuationapp.finalevacuationapp.Model.model;
import com.evacuationapp.finalevacuationapp.Model.model2;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListAddedReliefGoodsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    RecyclerView recview;
    myadapter2 adapter1;
    private FirebaseFirestore firestore;
    android.widget.SearchView SearchView;
    private Toolbar mainToolbar;
    Button btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_added_relief_goods);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        setTitle("Search here..");
        SearchView = findViewById(R.id.searchView);
        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        btnHome = findViewById(R.id.btnHome);
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        getSupportActionBar().setTitle("Evacuation");

        FirebaseRecyclerOptions<model2> options =
                new FirebaseRecyclerOptions.Builder<model2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("reliefgoods"), model2.class)
                        .build();
        adapter1 = new myadapter2(options);
        recview.setAdapter(adapter1);

btnHome.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(ListAddedReliefGoodsActivity.this, StaffHomeActivity.class));
    }
});




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(ListAddedReliefGoodsActivity.this, UserSignInActivity.class));
            finish();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.searchView);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<model2> options =
                new FirebaseRecyclerOptions.Builder<model2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("reliefgoods").orderByChild("addReliefGoodsEvacuationName").startAt(s).endAt(s+"\uf8ff"), model2.class)
                        .build();


        adapter1=new myadapter2(options);
        adapter1.startListening();
        recview.setAdapter(adapter1);

    }

}