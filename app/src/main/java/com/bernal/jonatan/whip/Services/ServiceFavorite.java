package com.bernal.jonatan.whip.Services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Favorite;
import com.bernal.jonatan.whip.Usuari_Logejat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServiceFavorite {


    private String Base_Url = "https://whip-api.herokuapp.com/contributions/lostposts/";
    private RequestQueue requestQueue;
    private Usuari_Logejat ul = Usuari_Logejat.getUsuariLogejat("");

    public ServiceFavorite (Context thisContext) {
        requestQueue = Volley.newRequestQueue(thisContext);
    }

    public Favorite getLike (String ID_Post) {


        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                Base_Url+ID_Post,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            Favorite fav = new Favorite();

                            boolean result = response.getBoolean("isFavorite");

                            fav.setFav(result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);


    }
}
