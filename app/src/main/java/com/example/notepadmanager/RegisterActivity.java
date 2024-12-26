package com.example.notepadmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepadmanager.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;  // Binding için nesne
    private FirebaseAuth mAuth;  // Firebase Auth nesnesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Binding nesnesi ile layout'u bağla
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FirebaseAuth nesnesini başlat
        mAuth = FirebaseAuth.getInstance();

        // Butona tıklama olayı ekle
        binding.btnRegisterConfirmRegister.setOnClickListener(v -> {
            // Kullanıcıdan bilgileri al
            String fullName = binding.etRegisterFullname.getText().toString().trim();
            String password = binding.etRegisterPassword.getText().toString().trim();
            String confirmPassword = binding.etRegisterPasswordAgain.getText().toString().trim();
            String email = binding.etRegisterEmail.getText().toString().trim();

            // Gerekli kontrolleri yap
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // E-posta geçerli mi diye kontrol et
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // Şifrelerin uyuşup uyuşmadığını kontrol et
            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Şifre uzunluğu kontrolü (8-20 karakter arası)
            if (password.length() < 8 || password.length() > 20) {
                Toast.makeText(RegisterActivity.this, "Password must be between 8 and 20 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase üzerinden kayıt işlemi
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Kayıt başarılı
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            // LoginActivity'ye yönlendir
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Mevcut RegisterActivity'yi kapat
                        } else {
                            // Kayıt başarısız
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;  // Memory leak önlemek için binding'i temizle
    }
}