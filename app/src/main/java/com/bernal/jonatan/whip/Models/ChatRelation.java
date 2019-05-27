package com.bernal.jonatan.whip.Models;

import com.bernal.jonatan.whip.Views.ChatList;

public class ChatRelation {

    private String id;

    private String otherUserId;

    private String otherUserName;

    private String photoUrl;


    public ChatRelation(String otherUser, String id) {
        this.otherUserId = otherUser;

        this.id = id;
    }

    public ChatRelation(String username, String photo_url, String id) {
        this.otherUserName = username;
        this.photoUrl = photo_url;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
