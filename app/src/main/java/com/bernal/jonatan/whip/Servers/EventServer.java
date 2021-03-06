package com.bernal.jonatan.whip.Servers;

import android.content.Context;
import android.widget.EditText;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

    private UserLoggedIn u1 = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = u1.getAPI_KEY();
    private RequestQueue requestQueue;

    public void getEvents(final EventPresenter eventPresenter, String URL) {
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                Method.GET,
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
                                User_events.add(new Event(usr_event.getString("userIdFromPost"), usr_event.getString("userId"),
                                        usr_event.getString("place"), usr_event.getString("date").split("T")[0],
                                        usr_event.getString("date").split("T")[1], usr_event.getString("id"), usr_event.getString("state")));
                            }
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

    public void updateEvent(final EventPresenter eventPresenter, String urlUpdateAccept) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth", u1.getToken());
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.PATCH,
                urlUpdateAccept,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        eventPresenter.recharge();

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
                params.put("Authorization", u1.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void getEventInfo(final EventPresenter eventPresenter, String url) {
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Event event = new Event(response.getString("userIdFromPost"), response.getString("userId"),
                                    response.getString("place"), response.getString("date").split("T")[0],
                                    response.getString("date").split("T")[1], response.getString("id"), response.getString("state"));
                            eventPresenter.setEvent(event);
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
                params.put("Authorization", u1.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void newQuedada(final EventPresenter eventPresenter, String postID, int año, String mes, String dia, String horaForm, String minForm, EditText lugar, String usernameFromPost, String type, String URL) {
        JSONObject quedada = new JSONObject();
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        try {
            if (type.equals("adoption")) {
                quedada.put("adoptionPostId", postID);
            } else {
                quedada.put("lostPostId", postID);
            }

            quedada.put("date", año + "-" + mes + "-" + dia + " " + horaForm + ":" + minForm + ": 00");
            quedada.put("place", lugar.getText().toString());
            quedada.put("userIdFromPost", usernameFromPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.POST,
                URL,
                quedada,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        eventPresenter.notifyNewQuedada();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);


    }

    public void replanificarQuedada(final EventPresenter eventPresenter, String postID, int año, String mes, String dia, String horaForm, String minForm, EditText lugar, String usernameFromPost, String URL) {
        JSONObject quedada = new JSONObject();
        requestQueue = Volley.newRequestQueue((Context) eventPresenter.getView());
        try {
            quedada.put("date", año + "-" + mes + "-" + dia + " " + horaForm + ":" + minForm + ": 00");
            quedada.put("place", lugar.getText().toString());
            quedada.put("userIdFromPost", usernameFromPost);
            quedada.put("userId", u1.getCorreo_user());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.PATCH,
                URL,
                quedada,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        eventPresenter.notifyReplanificar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }
}

