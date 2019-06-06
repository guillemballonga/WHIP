package com.bernal.jonatan.whip.Models;

public class Event {

    private String id;
    private String userId;
    private String userFromPostId;
    private String date;
    private String time;
    private String state;
    private String place;

    public Event(String userIdFromPost, String userId, String place, String date, String time, String idEvent, String state) {
        this.userFromPostId = userIdFromPost;
        this.userId = userId;
        this.place = place;
        this.date = date;
        this.time = time;
        this.id = idEvent;
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
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

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }
}
