package com.bernal.jonatan.whip.Presenters;

import com.bernal.jonatan.whip.Servers.AdoptionRequestServer;

public class AdoptionRequestPresenter {


    View view;

    AdoptionRequestServer adoptionRequestServer;

    public AdoptionRequestPresenter(View view) {
        this.view = view;
        this.adoptionRequestServer = new AdoptionRequestServer();
    }

    public Object getView() {
        return this.view;
    }

    public void sendInfo(String URL, String cosText, String adoptionPostID, String photo_url, String usernameFromPost) {
        adoptionRequestServer.sendInfo(this, URL, cosText, adoptionPostID, photo_url, usernameFromPost);
    }

    public void notifyCreate() {
        view.notifyCreate();
    }

    public void notifyEmptyDesc() {
        view.notifyEmptyDesc();
    }

    public interface View {

        void notifyCreate();

        void notifyEmptyDesc();
    }
}
