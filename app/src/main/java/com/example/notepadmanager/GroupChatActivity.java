package com.example.notepadmanager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notepadmanager.databinding.ActivityGroupChatBinding;
import com.example.notepadmanager.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private ActivityGroupChatBinding binding;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private DatabaseReference messagesRef;
    private String groupId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Firebase Initialization
        mAuth = FirebaseAuth.getInstance();
        groupId = getIntent().getStringExtra("groupId");
        messagesRef = FirebaseDatabase.getInstance().getReference("Groups").child(groupId).child("Messages");

        // RecyclerView Setup
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);
        binding.rvMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMessages.setAdapter(messageAdapter);

        // Back Button Listener
        binding.ivBack.setOnClickListener(v -> finish());

        // Send Button Listener
        binding.ivSend.setOnClickListener(v -> {
            String messageText = binding.etMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                sendMessage(messageText);
                binding.etMessage.setText("");
            } else {
                Toast.makeText(this, "Mesaj boş olamaz", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch Messages (Placeholder for Firebase Listener)
        fetchMessages();
    }

    private void sendMessage(String messageText) {
        String userId = mAuth.getCurrentUser().getUid();
        String messageId = messagesRef.push().getKey();

        Message message = new Message(messageId, userId, messageText);
        if (messageId != null) {
            messagesRef.child(messageId).setValue(message)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Mesaj gönderildi", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Mesaj gönderilemedi", Toast.LENGTH_SHORT).show());
        }
    }

    private void fetchMessages() {
        // Placeholder: Implement Firebase Realtime Database Listener
    }
}
