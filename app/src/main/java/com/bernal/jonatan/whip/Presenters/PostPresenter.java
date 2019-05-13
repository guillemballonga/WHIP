package com.bernal.jonatan.whip.Presenters;

import android.view.View;

import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Servers.PostServer;

import java.util.ArrayList;

public class PostPresenter {


    View view;

    PostServer postServer;

    public PostPresenter(View view) {
        this.view = view;
        this.postServer = new PostServer();
    }

    public void getAdoptionPosts(String URL) {
        postServer.getAdoptionPosts(this, URL);
    }

    public void getLostPosts(String URL) {
        postServer.getLostsPosts(this, URL);
    }

    public void chargeAdoptionList(ArrayList posts) {
        view.chargeAdoptionList(posts);
    }

    public void chargeLostList(ArrayList posts) {
        view.chargeLostList(posts);
    }

    public interface View {

        void chargeAdoptionList(ArrayList posts);

        void chargeLostList(ArrayList posts);
    }

}
