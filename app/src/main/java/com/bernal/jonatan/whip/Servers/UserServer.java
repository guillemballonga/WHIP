package com.bernal.jonatan.whip.Servers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.User;
import com.bernal.jonatan.whip.Presenters.UserPresenter;
import com.bernal.jonatan.whip.Views.EditProfile;
import com.bernal.jonatan.whip.Views.MostrarPerfil;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserServer {

    static String URL = "https://whip-api.herokuapp.com/users/profile";
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    private RequestQueue requestQueue;

    public void getUser(final UserPresenter userPresenter) {

        requestQueue = Volley.newRequestQueue((Context) userPresenter.getView());
        JsonObjectRequest arrayJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            User user = new User();
                            user.setCp(response.getString("post_code"));
                            user.setEmail(response.getString("email"));
                            user.setFamily_name(response.getString("family_name"));
                            user.setFirst_name(response.getString("first_name"));
                            user.setUsername(response.getString("username"));
                            user.setPhotoURL(response.getString("photo_url"));
                            userPresenter.setUser(user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        requestQueue.add(arrayJsonrequest);
    }

    public void modifyUser(final UserPresenter userPresenter, JSONObject perfil_editat) {

        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.PATCH,
                URL,
                perfil_editat,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //poner alguna función vacía para indicar que ha terminado?
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
