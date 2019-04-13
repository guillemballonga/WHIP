package com.bernal.jonatan.whip.Services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Favorite;
import com.bernal.jonatan.whip.Usuari_Logejat;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java8.util.concurrent.CompletableFuture;
import java8.util.function.Supplier;

public class ServiceFavorite {


    private String Base_Url = "https://whip-api.herokuapp.com/contributions/lostposts/";
    private RequestQueue requestQueue;
    private Usuari_Logejat ul = Usuari_Logejat.getUsuariLogejat("");
    Favorite fav = new Favorite();





    public ServiceFavorite (Context thisContext) {
        requestQueue = Volley.newRequestQueue(thisContext);
    }

    public Favorite getLike (final String ID_Post) {

            JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                    JsonRequest.Method.GET,
                   Base_Url + ID_Post + "/like",
                   null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                             try {
                                 fav.setFav(response.getBoolean("isFavorite"));
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
                 public Map<String, String> getHeaders() throws AuthFailureError {
                     Map<String, String> params = new HashMap<>();
                     params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                     return params;
                 }
            };
            requestQueue.add(objectJsonrequest);
            return fav;
    }


    public void setDislike(String post_ID) {

        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.DELETE,
                Base_Url+post_ID+"/like",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }


    public void setLike(String post_ID) {

        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.POST,
                Base_Url+post_ID+"/like",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }



}
