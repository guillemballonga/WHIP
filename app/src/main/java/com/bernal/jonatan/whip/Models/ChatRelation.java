package com.bernal.jonatan.whip.Models;

import com.bernal.jonatan.whip.Views.ChatList;

public class ChatRelation {

    private String id;

    private String userIdOne;

    private String userIdTwo;

    private String photoUrl;


    public ChatRelation(String id, String userIdOne, String userIdTwo) {
        this.id = id;
        this.userIdOne = userIdOne;
        this.userIdTwo = userIdTwo;
    }


    public String getId() {
        return id;
    }

    public String getUserIdOne() {
        return userIdOne;
    }

    public String getUserIdTwo() {
        return userIdTwo;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserIdOne(String userIdOne) {
        this.userIdOne = userIdOne;
    }

    public void setuserIdTwo(String userIdTwo) {
        this.userIdTwo = userIdTwo;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
