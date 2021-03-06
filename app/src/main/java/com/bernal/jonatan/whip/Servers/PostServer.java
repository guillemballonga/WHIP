package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Presenters.PostPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PostServer {

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = ul.getAPI_KEY();
    private RequestQueue requestQueue;


    public void getAdoptionPosts(final PostPresenter postPresenter, String URL) {

        requestQueue = Volley.newRequestQueue((Context) postPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList Posts_adoption = new ArrayList<>();
                            JSONObject postite;
                            for (int i = 0; i < response.length(); i++) {
                                postite = response.getJSONObject(i);
                                Posts_adoption.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), "ADOPTION"));
                            }
                            postPresenter.chargeAdoptionList(Posts_adoption);

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
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(arrayJsonrequest);


    }

    public void getLostsPosts(final PostPresenter postPresenter, String URL) {

        requestQueue = Volley.newRequestQueue((Context) postPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList Posts_perdidos = new ArrayList<>();
                            JSONObject postite;
                            for (int i = 0; i < response.length(); i++) {
                                postite = response.getJSONObject(i);
                                Posts_perdidos.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), "LOST"));
                            }
                            postPresenter.chargeLostList(Posts_perdidos);
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
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(arrayJsonrequest);
    }

    public void searchPost(final PostPresenter postPresenter, String text, String URL) {
        text = text.replace(" ", "+");
        URL += "?text=" + text;

        requestQueue = Volley.newRequestQueue((Context) postPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList Posts_perdidos = new ArrayList<>();
                            JSONObject postite;
                            for (int i = 0; i < response.length(); i++) {
                                postite = response.getJSONObject(i);
                                Posts_perdidos.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), "LOST"));
                            }
                            postPresenter.chargeLostList(Posts_perdidos);
                            postPresenter.chargeAdoptionList(Posts_perdidos);
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
                params.put("Content-Type", "application/json");
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(arrayJsonrequest);
    }
}
