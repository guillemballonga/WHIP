package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConcretePostServer {


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private RequestQueue requestQueue;



    public void getPost(final ConcretePostPresenter concretePostPresenter, String URL, final String tipo_post) {
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject result = response.getJSONObject("postInfo");
                            String title = result.getString("title");
                            String[] data = (result.getString("createdAt")).split("T");
                            String specie = result.getString("specie");
                            String race = result.getString("race");
                            String text = result.getString("text");
                            String userId = result.getString("userId");
                            String photo_url_1 = result.getString("photo_url_1");
                            String username = result.getString("username");
                            String type = "";
                            if (tipo_post.equals("Lost")) {
                                type = result.getString("type");
                            }

                            JSONObject geo = result.getJSONObject("geolocation");
                            JSONArray arrJson = geo.getJSONArray("coordinates");
                            String[] arr = new String[arrJson.length()];
                            for (int i = 0; i < arrJson.length(); i++)
                                arr[i] = arrJson.getString(i);
                            String coord1 = arr[0];
                            String coord2 = arr[1];
                            Boolean status = result.getBoolean("status");
                            Post post = new Post(title, data, specie, race, text, userId, photo_url_1, status, type, username, coord1, coord2);
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


    public void closePost(final ConcretePostPresenter concretePostPresenter, String URL_close) {
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.PATCH,
                URL_close,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        concretePostPresenter.recharge();
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
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }

    public void getFavorite(final ConcretePostPresenter concretePostPresenter, String URL_favs) {
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest3 = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL_favs,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            boolean fav = response.getBoolean("isFavorite");
                            concretePostPresenter.setFavorite(fav);
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
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest3);
    }

    public void deletePost(final ConcretePostPresenter concretePostPresenter, String URL) {
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.DELETE,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        concretePostPresenter.setDeletePost();
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
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }

    public void likePost(final ConcretePostPresenter concretePostPresenter, String URL_like, Boolean like) {
        int method;
        if (like) method = JsonRequest.Method.POST;
        else method = JsonRequest.Method.DELETE;
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                method,
                URL_like,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        concretePostPresenter.recharge();
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
                params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }

    public void createPost(final ConcretePostPresenter concretePostPresenter, String URL, String especie, String identificadorImatge, String race, String cp, String text, String title, String type, String tipusPost) {
        JSONObject post = new JSONObject();
        JSONArray photos = new JSONArray();
        photos.put(identificadorImatge);
        photos.put("");
        photos.put("");
        photos.put("");
        try {
            post.put("specie", especie);
            post.put("urls", photos);
            post.put("race", race);
            post.put("post_code", cp);
            post.put("text", text);
            post.put("title", title);

            if (tipusPost.equals("Lost")) {

                if (type.equals("Encontrado")) post.put("type", "F");
                else post.put("type", "L");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue((Context) concretePostPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.POST,
                URL,
                post,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            concretePostPresenter.notifyCreate(response.getString("id"));
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
                params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }

    public void shareFacebook() {

    }

}


