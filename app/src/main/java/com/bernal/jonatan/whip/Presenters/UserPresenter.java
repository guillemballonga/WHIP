package com.bernal.jonatan.whip.Presenters;

import android.content.Context;

import com.bernal.jonatan.whip.Models.User;
import com.bernal.jonatan.whip.Servers.UserServer;

import java.util.ArrayList;

public class UserPresenter {


    View view;

    UserServer userServer;

    public UserPresenter(View view){
        this.view = view;
        this.userServer = new UserServer();
    }

    public void getUser(){
        userServer.getUser(this);
    }

    public void setUser(User user){
        view.getUserInfo(user.getCp(),user.getEmail(),user.getFamily_name(),user.getFirst_name(),user.getPhotoURL(),user.getUsername());
    }

    public void modifyUser(String cp, String nom, String cognom, String user, String urlFoto) {
        userServer.modifyUser(this, cp, nom, cognom, user, urlFoto);
    }

    public void getUserPosts(String URL) {
        userServer.getUserPosts(URL, this);
    }


    public View getView(){
        return view;
    }

    public void setActivity() {
        view.changeActivity();
    }

    public void setUserPosts(ArrayList mis_posts) {
        view.setUserPosts(mis_posts);
    }

    public void getOthersInfo(ArrayList user_chats) {
        userServer.getOthersInfo(this, user_chats);
    }

    public void sendInfoForChat(ArrayList userInfoForChat) {
        view.sendInfoForChat(userInfoForChat);
    }


    public interface View{
        void getUserInfo(String cp, String email, String family_name, String first_name,String photoURL, String username);
        void changeActivity();

        void setUserPosts(ArrayList mis_posts);

        void sendInfoForChat(ArrayList userInfoForChat);
    }


}

