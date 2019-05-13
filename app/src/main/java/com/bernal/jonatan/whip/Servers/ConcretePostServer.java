package com.bernal.jonatan.whip.Servers;

import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConcretePostServer {


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    private RequestQueue requestQueue;

    public void getPost(final ConcretePostPresenter concretePostPresenter, String URL) {
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //    Post post = new Post(response.getString(""));
                            //Aquí irán todos los post.set lo q sea
                            String title = response.getString("title");
                            String[] data = (response.getString("createdAt")).split("T");
                            String specie = response.getString("specie");
                            String race = response.getString("race");
                            String text = response.getString("text");
                            String userId = response.getString("userId");
                            String photo_url_1 = response.getString("photo_url_1");
                            Boolean status = response.getBoolean("status");
                            Post post = new Post(title, data, specie, race, text, userId, photo_url_1, status);
                            concretePostPresenter.setPost(post);

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
                params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }
}


