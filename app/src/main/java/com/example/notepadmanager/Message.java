package com.example.notepadmanager.models;

public class Message {
    private String messageId;
    private String userId;
    private String messageText;

    // Boş Constructor (Firebase için gerekli)
    public Message() {
    }

    // Parametreli Constructor
    public Message(String messageId, String userId, String messageText) {
        this.messageId = messageId;
        this.userId = userId;
        this.messageText = messageText;
    }

    // Getter ve Setter Metotları
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
