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

    public void getPost(String URL, String tipo_post) {
        concretePostServer.getPost(this, URL, tipo_post);
    }

    public void closePost(String URL_close) {
        concretePostServer.closePost(this, URL_close);
    }

    public void deletePost(String URL) {
        concretePostServer.deletePost(this, URL);
    }


    public void likePost(String URL_like, Boolean like) {
        concretePostServer.likePost(this, URL_like, like);
    }


    public void createPost(String URL, String especie, String urls, String race, String cp, String text, String title, String type, String tipuspost) {
        concretePostServer.createPost(this, URL, especie, urls, race, cp, text, title, type, tipuspost);
    }
/*
 post.put("specie", especie.getSelectedItem().toString());
                    post.put("urls", k);
                    post.put("race", raza.getText().toString());
                    post.put("post_code", cp.getText().toString());
                    post.put("text", contenido.getText().toString());
                    post.put("title", titulo.getText().toString());
                    if (tipo.getSelectedItem().toString().equals("Encontrado"))
                        post.put("type", "F");
                    else post.put("type", "L");

 */

    public View getView() {
        return view;
    }

    public void setPost(Post post) {
        view.getPostInfo(post.getTitle(), post.getCreatedAt(), post.getSpecie(), post.getRace(), post.getContenido(), post.getUserId(), post.getImagen(), post.getStatus(), post.getType());
    }


    public void getFavorite(String URL_favs) {
        concretePostServer.getFavorite(this, URL_favs);
    }

    public void setFavorite(Boolean fav) {
        view.setFavorite(fav);
    }

    public void setDeletePost() {
        view.setDeletePost();
    }

    public void recharge() {
        view.recharge();
    }

    public void notifyCreate(String id) {
        view.notifyCreate(id);
    }


    public interface View {

        void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type);

        void setFavorite(Boolean fav);

        void setDeletePost();

        void recharge();

        void notifyCreate(String id);
    }

}
