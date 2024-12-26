package com.example.notepadmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText etGroupName, etGroupPassword;
    private Button btnCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        // Initialize views
        etGroupName = findViewById(R.id.etGroupName);
        etGroupPassword = findViewById(R.id.etGroupPassword);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);

        btnCreateGroup.setOnClickListener(v -> {
            String groupName = etGroupName.getText().toString();
            String groupPassword = etGroupPassword.getText().toString();

            if (groupName.isEmpty() || groupPassword.isEmpty()) {
                Toast.makeText(CreateGroupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Logic to create group (e.g., store group info in Firebase)
            Toast.makeText(CreateGroupActivity.this, "Group created successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
