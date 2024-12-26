package com.example.notepadmanager;

public class Group {
    private String groupName;
    private String groupId;

    public Group() {
        // Firebase için boş constructor gerekli
    }

    public Group(String groupName, String groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}