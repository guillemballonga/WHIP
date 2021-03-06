package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.CustomComparator;
import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Models.User;
import com.bernal.jonatan.whip.Presenters.UserPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserServer {

    static String URL = "https://whip-api.herokuapp.com/users/profile";
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
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

    public void modifyUser(final UserPresenter userPresenter, String cp, String nom, String cognom, String user, String urlFoto) {

        JSONObject perfil_editat = new JSONObject();
        try {
            perfil_editat.put("post_code", cp);
            perfil_editat.put("name", nom);
            perfil_editat.put("fam_name", cognom);
            perfil_editat.put("username", user);
            perfil_editat.put("photo_url", urlFoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.PATCH,
                URL,
                perfil_editat,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        userPresenter.setActivity();
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

    public void getOthersInfo(final UserPresenter userPresenter, final ArrayList user_chats) {
        Collections.sort(user_chats, new CustomComparator());  //Ahora tengo ordenados los user_chats por orden alfabetico

        //Tenemos ordenados los ids que vamos a buscar, esto está hecho así para que se corresponda con el orden en el que nos los devolverá
        //back, así nos ahorramos el coste de asociar los ids con los resultados que obtenemos en back
        String URL = "https://whip-api.herokuapp.com/users/profile/list?";
        ChatRelation cr;
        if (user_chats.size() > 0) {
            cr = (ChatRelation) user_chats.get(0);
            URL = URL + "id=" + cr.getOtherUserId();
            for (int j = 1; j < user_chats.size(); ++j) {
                cr = (ChatRelation) user_chats.get(j);
                URL += "&id=" + cr.getOtherUserId();
            }
        }

        //Aqui termina la preparación de los datos para hacer la llamada a back

        requestQueue = Volley.newRequestQueue((Context) userPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList userInfoForChat = new ArrayList();
                            JSONObject userNP;
                            for (int i = 0; i < response.length(); ++i) {
                                userNP = response.getJSONObject(i);
                                ChatRelation cr = (ChatRelation) user_chats.get(i);
                                userInfoForChat.add(new ChatRelation(userNP.getString("username"), userNP.getString("photo_url"), cr.getId()));
                            }
                            userPresenter.sendInfoForChat(userInfoForChat);

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
}
