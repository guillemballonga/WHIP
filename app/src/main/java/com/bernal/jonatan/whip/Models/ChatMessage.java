package com.bernal.jonatan.whip.Models;

public class ChatMessage {

    private String id;

    private String userId;

    private String date;

    private String time;

    private String message;

    public ChatMessage(String id, String userId, String message, String date, String time) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
