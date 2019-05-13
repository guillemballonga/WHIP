package com.bernal.jonatan.whip.Presenters;

import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Servers.ConcretePostServer;

import java.util.Date;

public class ConcretePostPresenter {
    View view;

    ConcretePostServer concretePostServer;

    public ConcretePostPresenter(View view) {
        this.view = view;
        this.concretePostServer = new ConcretePostServer();
    }

    public void getPost(String URL) {
        concretePostServer.getPost(this, URL);
    }

    public void setPost(Post post) {
        view.getPostInfo(post.getTitle(), post.getCreatedAt(), post.getSpecie(), post.getRace(), post.getContenido(), post.getUserId(), post.getImagen(), post.getStatus());
        //No te haría falta esto también en el modelo ?? title, Date createdAt, String specie, String userId, Boolean status)
    }


    public void getFavorite() {
    }


    public interface View {

        //Post post = new Post(title,data,specie,race,text,userId,photo_url_1, status);
        void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status);


    }

}
