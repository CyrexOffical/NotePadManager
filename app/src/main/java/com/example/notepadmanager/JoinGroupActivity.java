package com.example.notepadmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepadmanager.databinding.ActivityJoinGroupBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinGroupActivity extends AppCompatActivity {

    private ActivityJoinGroupBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference groupsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewBinding başlat
        binding = ActivityJoinGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Firebase veritabanı bağlantısını kur
        database = FirebaseDatabase.getInstance();
        groupsRef = database.getReference("Groups");

        // Grup katılma butonuna tıklama olayı
        binding.btnJoinGroup.setOnClickListener(v -> {
            String groupId = binding.etGroupId.getText().toString().trim();
            String groupPassword = binding.etGroupPasswordJoin.getText().toString().trim();

            // Alanların boş olup olmadığını kontrol et
            if (groupId.isEmpty() || groupPassword.isEmpty()) {
                Toast.makeText(JoinGroupActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase veritabanında grubu bulma
            groupsRef.child(groupId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    String storedPassword = task.getResult().child("password").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(groupPassword)) {
                        // Şifre doğruysa, kullanıcıyı grup sohbetine yönlendir
                        Intent intent = new Intent(JoinGroupActivity.this, GroupChatActivity.class);
                        intent.putExtra("groupId", groupId); // Grup ID'sini geçiriyoruz
                        startActivity(intent);
                    } else {
                        // Şifre yanlış
                        Toast.makeText(JoinGroupActivity.this, "Yanlış şifre", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Grup bulunamadı
                    Toast.makeText(JoinGroupActivity.this, "Grup bulunamadı", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
