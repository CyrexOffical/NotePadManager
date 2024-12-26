package com.example.notepadmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail, tvProfilePassword;
    private Button btnShowPassword, btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfilePassword = findViewById(R.id.tvProfilePassword);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnLogout = findViewById(R.id.btnLogout);

        // Set Profile information
        tvProfileName.setText("Name: " + mAuth.getCurrentUser().getDisplayName());
        tvProfileEmail.setText("Email: " + mAuth.getCurrentUser().getEmail());

        // Show password logic
        btnShowPassword.setOnClickListener(v -> {
            tvProfilePassword.setText("Password: " + mAuth.getCurrentUser().getUid());
        });

        // Logout functionality
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            finish(); // Close profile activity and return to login
        });
    }
}
