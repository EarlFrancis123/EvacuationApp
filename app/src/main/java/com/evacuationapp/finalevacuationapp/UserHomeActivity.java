package com.evacuationapp.finalevacuationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.evacuationapp.finalevacuationapp.Adapter.PostAdapter;
import com.evacuationapp.finalevacuationapp.Model.Post;
import com.evacuationapp.finalevacuationapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private Toolbar mainToolbar;
    private FirebaseFirestore firestore;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private PostAdapter adapter;
    private List<Post> list;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private List<Users> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mainToolbar = findViewById(R.id.main_toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(UserHomeActivity.this));
        list = new ArrayList<>();
        usersList = new ArrayList<>();
        adapter = new PostAdapter(UserHomeActivity.this , list, usersList);
        mRecyclerView.setAdapter(adapter);
        fab = findViewById(R.id.floatingActionButton);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Evacuation");
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.userlandingpage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this , UserAddPostActivity.class));
            }
        });
        if (firebaseAuth.getCurrentUser() != null){

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean isBottom = !mRecyclerView.canScrollVertically(1);
                    if (isBottom)
                        Toast.makeText(UserHomeActivity.this, "Reached Bottom", Toast.LENGTH_SHORT).show();
                }
            });
            query = firestore.collection("Posts").orderBy("time" , Query.Direction.DESCENDING);
            listenerRegistration = query.addSnapshotListener(UserHomeActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for (DocumentChange doc : value.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){
                            String postId = doc.getDocument().getId();
                            Post post = doc.getDocument().toObject(Post.class).withId(postId);
                            String postUserId = doc.getDocument().getString("user");
                            firestore.collection("Users").document(postUserId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()){
                                                Users users = task.getResult().toObject(Users.class);
                                                usersList.add(users);
                                                list.add(post);
                                                adapter.notifyDataSetChanged();
                                            }else{
                                                Toast.makeText(UserHomeActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                    listenerRegistration.remove();
                }
            });

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(UserHomeActivity.this , UserSignInActivity.class));
            finish();
        }else{
            String currentUserId = firebaseAuth.getCurrentUser().getUid();
            firestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        if (!task.getResult().exists()){
                            startActivity(new Intent(UserHomeActivity.this , UserSetUpActivity.class));
                            finish();
                        }
                    }
                }
            });
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.rescue:
                            startActivity(new Intent(getApplicationContext(),UserRescueActivity.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.userlandingpage:
                            return true;

                        case R.id.searchevacuee:
                            startActivity(new Intent(getApplicationContext(),UserSearchEvacueeActivity.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.nearest:
                            startActivity(new Intent(getApplicationContext(),GetLatLong.class));
                            overridePendingTransition(0,0);
                            return true;


                    }
                    return false;
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2 , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(UserHomeActivity.this , UserSetUpActivity.class));
        }else if(item.getItemId() == R.id.sign_out_menu){
            firebaseAuth.signOut();
            startActivity(new Intent(UserHomeActivity.this , UserSignInActivity.class));
            finish();
        }
        return true;
    }
}
