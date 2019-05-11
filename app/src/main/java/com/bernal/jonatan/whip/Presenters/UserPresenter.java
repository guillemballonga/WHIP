package com.bernal.jonatan.whip.Presenters;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bernal.jonatan.whip.Models.User;
import com.bernal.jonatan.whip.Servers.UserServer;
import com.bernal.jonatan.whip.Views.EditProfile;
import com.bernal.jonatan.whip.Views.MostrarPerfil;

public class UserPresenter {


    MostrarPerfil mostrarPerfil;

    UserServer userServer;

    public UserPresenter(MostrarPerfil mostrarPerfil){
        this.mostrarPerfil = mostrarPerfil;
        this.userServer = new UserServer();
    }

    public void getUser(){
        userServer.getUser(this);
    }

    public void setUser(User user){
        mostrarPerfil.getUserInfo(user.getCp(),user.getEmail(),user.getFamily_name(),user.getFirst_name(),user.getPhotoURL(),user.getUsername());
    }

    public MostrarPerfil getMostrarPerfil() {
        return mostrarPerfil;
    }

}

