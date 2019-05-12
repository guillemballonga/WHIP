package com.bernal.jonatan.whip.Presenters;

import android.view.View;

import com.bernal.jonatan.whip.Servers.PostServer;

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

    public interface View {

        void changeActivity();
    }

}
