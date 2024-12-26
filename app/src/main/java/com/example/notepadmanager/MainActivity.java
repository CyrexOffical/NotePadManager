package com.example.notepadmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadmanager.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private GroupAdapter groupAdapter;
    private List<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("groups");

        groupList = new ArrayList<>();
        groupAdapter = new GroupAdapter(this, groupList, group -> {
            Intent intent = new Intent(MainActivity.this, GroupDetailActivity.class);
            intent.putExtra("groupId", group.getGroupId());
            startActivity(intent);
        });

        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setAdapter(groupAdapter);


        binding.btnCreateGroup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });

        binding.btnJoinGroup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JoinGroupActivity.class);
            startActivity(intent);
        });

        binding.btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        loadGroups();
    }

    private void loadGroups() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Group group = snapshot.getValue(Group.class);
                    if (group != null) {
                        groupList.add(group);
                    }
                }
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load groups.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
