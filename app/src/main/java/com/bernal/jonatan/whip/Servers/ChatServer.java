package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.Presenters.ChatPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private UserLoggedIn u1 = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = u1.getAPI_KEY();
    private RequestQueue requestQueue;


    public void getChats(final ChatPresenter chatPresenter, String URL) {

        requestQueue = Volley.newRequestQueue((Context) chatPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList User_chats = new ArrayList<>();
                            JSONObject usr_chat = new JSONObject();
                            String otherUser;
                            for (int i = 0; i < response.length(); ++i) {
                                usr_chat = response.getJSONObject(i);
                                if (u1.getCorreo_user().equals(usr_chat.getString("userId")))
                                    otherUser = usr_chat.getString("userId2");
                                else otherUser = usr_chat.getString("userId");
                                User_chats.add(new ChatRelation(otherUser, usr_chat.getString("id")));
                            }
                            chatPresenter.chargeChats(User_chats);
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

    public void deleteChat(String url_chats, String id_chat) {
    }
}

