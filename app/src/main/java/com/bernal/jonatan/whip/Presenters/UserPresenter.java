package com.bernal.jonatan.whip.Presenters;

import android.view.View;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bernal.jonatan.whip.Models.User;
import com.bernal.jonatan.whip.Servers.UserServer;
import com.bernal.jonatan.whip.Views.EditProfile;
import com.bernal.jonatan.whip.Views.MostrarPerfil;

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

    public void modifyUser() {
        userServer.modifyUser(this);
    }


    public View getView(){
        return view;
    }

    public interface View{
        void getUserInfo(String cp, String email, String family_name, String first_name,String photoURL, String username);
    }


}

