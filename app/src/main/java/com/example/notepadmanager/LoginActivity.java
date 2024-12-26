package com.example.notepadmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepadmanager.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewBinding başlatma
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // FirebaseAuth başlatma
        mAuth = FirebaseAuth.getInstance();

        // Kayıt sayfasına yönlendirme
        binding.tvLoginNotRegistered.setOnClickListener(view1 -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Giriş yap butonuna tıklama olayı
        binding.btnLoginConfirmLogin.setOnClickListener(view12 -> {
            String email = binding.etLoginEmail.getText().toString().trim();
            String password = binding.etLoginPassword.getText().toString().trim();

            // Alanları kontrol etme
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase ile giriş yapma
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Giriş başarılı
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Mevcut LoginActivity'yi kapat
                        } else {
                            // Giriş başarısız
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
