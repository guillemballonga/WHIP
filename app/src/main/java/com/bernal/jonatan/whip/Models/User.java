package com.bernal.jonatan.whip.Models;

public class User {

    private String username;
    private String first_name;
    private String family_name;
    private String mail;
    private String post_code;
    private String photo_url;

    public User() {
    }

    public User(String username, String first_name, String family_name, String mail, String post_code, String photo_url) {
        this.username = username;
        this.first_name = first_name;
        this.family_name = family_name;
        this.mail = mail;
        this.post_code = post_code;
        this.photo_url = photo_url;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }






}
