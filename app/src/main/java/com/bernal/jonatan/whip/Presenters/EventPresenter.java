package com.bernal.jonatan.whip.Presenters;

import android.view.View;

import com.bernal.jonatan.whip.Servers.EventServer;
import com.bernal.jonatan.whip.Views.EventList;

import java.util.ArrayList;

public class EventPresenter {

    View view;
    EventServer eventServer;

    public EventPresenter(View view) {
        this.view = view;
        this.eventServer = new EventServer();
    }

    public void getEvents(String URL) {
        eventServer.getEvents(this, URL);
    }

    public void chargeEvents(ArrayList events) {
        view.chargeEvents(events);
    }


    public View getView() {
        return this.view;
    }

    public interface View {

        void chargeEvents(ArrayList events);
    }
}
