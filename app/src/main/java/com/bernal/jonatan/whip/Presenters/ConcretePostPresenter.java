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

    public void closePost(String URL_close) {
        concretePostServer.closePost(this, URL_close);
    }

    public void deletePost(String URL) {
        concretePostServer.deletePost(this, URL);
    }

    public void dislikePost(String URL_like) {
        concretePostServer.dislikePost(this, URL_like);
    }

    public void likePost(String URL_like) {
        concretePostServer.likePost(this, URL_like);
    }


    public View getView() {
        return view;
    }

    public void setPost(Post post) {
        view.getPostInfo(post.getTitle(), post.getCreatedAt(), post.getSpecie(), post.getRace(), post.getContenido(), post.getUserId(), post.getImagen(), post.getStatus());
    }

    public void setClose() {
        view.setClose();
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


    public interface View {

        void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status);

        void setClose();

        void setFavorite(Boolean fav);

        void setDeletePost();

        void recharge();
    }

}
