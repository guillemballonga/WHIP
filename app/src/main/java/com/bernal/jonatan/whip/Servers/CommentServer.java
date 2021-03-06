package com.bernal.jonatan.whip.Servers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Comment;
import com.bernal.jonatan.whip.Presenters.CommentPresenter;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentServer {


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private RequestQueue requestQueue;

    public void createComment(final CommentPresenter commentPresenter, String URL_comments, String boxtext, String id_comment_parent) {
        JSONObject post = new JSONObject();
        try {
            post.put("text", boxtext);
            if (!id_comment_parent.equals("")) {
                post.put("parentComment", id_comment_parent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue((Context) commentPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.POST,
                URL_comments,
                post,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        commentPresenter.recharge();
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

    public void getComments(final CommentPresenter commentPresenter, final String URL_comments) {
        requestQueue = Volley.newRequestQueue((Context) commentPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL_comments,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList Comments_post = new ArrayList<>();
                            JSONObject comment;
                            for (int i = 0; i < response.length(); i++) {
                                comment = response.getJSONObject(i);
                                Comments_post.add(new Comment(comment.getString("id"), comment.getString("userId"), " ", comment.getString("text"), comment.getString("createdAt").split("T")[0]));
                            }

                            commentPresenter.chargeCommentList(Comments_post);

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
        requestQueue.add(arrayJsonrequest);
    }

    public void deleteComment(final CommentPresenter commentPresenter, String URL_comments, String id_comment) {
        requestQueue = Volley.newRequestQueue((Context) commentPresenter.getView());
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.DELETE,
                URL_comments + "/" + id_comment,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        commentPresenter.recharge();
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
                params.put("Authorization", ul.getAPI_KEY());
                return params;
            }
        };
        requestQueue.add(objectJsonrequest);
    }
}
