package com.bernal.jonatan.whip.Presenters;

import com.bernal.jonatan.whip.Models.Event;
import com.bernal.jonatan.whip.Servers.EventServer;

import org.json.JSONException;

import java.util.ArrayList;

public class EventPresenter {

    View view;
    EventServer eventServer;

    public EventPresenter(View view) {
        this.view = view;
        this.eventServer = new EventServer();
    }

    public void setEvent(Event event) {
        view.setEvent(event.getUserFromPostId(), event.getUserId(), event.getPlace(), event.getDate(), event.getTime());
    }


    public void getEvents(String URL) {
        eventServer.getEvents(this, URL);
    }

    public void getEventInfo(String URL) {
        eventServer.getEventInfo(this, URL);
    }

    public void chargeEvents(ArrayList events) {
        view.chargeEvents(events);
    }


    public View getView() {
        return this.view;
    }

    public void updateEvent(String urlUpdate) throws JSONException {
        eventServer.updateEvent(this, urlUpdate);

    }


    public void recharge() {
        view.recharge();
    }

    public interface View {
        void setEvent(String UserFromPostId, String UserId, String Place, String Date, String Time);
        void chargeEvents(ArrayList events);
        void recharge();
    }
}
