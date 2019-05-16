package com.bernal.jonatan.whip.Models;

import java.util.Date;

public class Event {

    private String id;
    private String userId;
    private String userFromPostId;
    private String date;
    private String time;
    private String state;
    private String place;
    private String postId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

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

    public String getUserFromPostId() {
        return userFromPostId;
    }

    public void setUserFromPostId(String userFromPost) {
        this.userFromPostId = userFromPost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
