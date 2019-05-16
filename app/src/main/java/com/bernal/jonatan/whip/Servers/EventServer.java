package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Event;
import com.bernal.jonatan.whip.Presenters.EventPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventServer {

    static String URL = "https://whip-api.herokuapp.com/events";
    private UserLoggedIn u1 = UserLoggedIn.getUsuariLogejat("", "");
    private String api = u1.getAPI_KEY();
    private RequestQueue requestQueue;

    public void getEvents(final EventPresenter eventPresenter, String URL) {
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList User_events = new ArrayList<>();
                            JSONObject usr_event = new JSONObject();
                            for (int i = 0; i < response.length(); ++i) {
                                usr_event = response.getJSONObject(i);
                            }
//UserFromPost, date, place
                            User_events.add(new Event(usr_event.getString("userIdFromPost"), usr_event.getString("userId"), usr_event.getString("place"), usr_event.getString("date").split("T")[0], usr_event.getString("date").split("T")[1]));

                            eventPresenter.chargeEvents(User_events);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", api);
                return params;
            }
        };
        requestQueue.add(arrayJsonrequest);
    }
}
