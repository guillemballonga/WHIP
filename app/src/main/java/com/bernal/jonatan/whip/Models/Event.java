package com.bernal.jonatan.whip.Models;

import java.util.Date;

public class Event {

    private String id;
    private String userId;
    private String userFromPost;
    private Date date;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFromPost() {
        return userFromPost;
    }

    public void setUserFromPost(String userFromPost) {
        this.userFromPost = userFromPost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
