package com.bernal.jonatan.whip.Models;

import java.util.Date;

public class Comment {


    private String user_comment;
    private String foto_user;
    private String text;
    private Date created_at;
    private String parent;

    public Comment() {
    }

    public Comment(String user_comment, String foto_user, String text, Date created_at, String parent) {
        this.user_comment = user_comment;
        this.foto_user = foto_user;
        this.text = text;
        this.created_at = created_at;
        this.parent = parent;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getFoto_user() {
        return foto_user;
    }

    public void setFoto_user(String foto_user) {
        this.foto_user = foto_user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
