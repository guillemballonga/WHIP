package com.bernal.jonatan.whip.Servers;

import android.content.Context;
import android.support.v7.util.SortedList;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    public void getUserPosts(String URL, final UserPresenter userPresenter) {
        requestQueue = Volley.newRequestQueue((Context) userPresenter.getView());
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList Mis_Posts = new ArrayList<>();
                            //            LinearLayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
                            //            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            JSONObject postite;
                            String tipo;
                            for (int i = 0; i < response.length(); i++) {
                                postite = response.getJSONObject(i);
                                if (postite.has("type")) tipo = "LOST";
                                else tipo = "ADOPTION";
                                Mis_Posts.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), tipo));
                            }
                            userPresenter.setUserPosts(Mis_Posts);
            /*                adapt = new PostAdapter(Mis_Posts, "PostPropio");
                            contenedor.setAdapter(adapt);
                            contenedor.setLayoutManager(layout);
                            adapt.setOnListListener(new OnListListener() {
                                @Override
                                public void onPostClicked(int position, View vista) {
                                    String id_post = Mis_Posts.get(contenedor.getChildAdapterPosition(vista)).getId();
                                    Intent i;
                                    if (Mis_Posts.get(contenedor.getChildAdapterPosition(vista)).getType().equals("LOST"))
                                        i = new Intent(getActivity(), InfoPostLost.class);
                                    else i = new Intent(getActivity(), InfoPostAdoption.class);
                                    i.putExtra("identificadorPost", id_post);
                                    startActivity(i);
                                }
                            });*/
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

    public void getOthersInfo(final UserPresenter userPresenter, final ArrayList user_chats) {
  /*      final ArrayList<String> userIds = new ArrayList<>();
        ArrayList<String> chatIdsNotSorted = new ArrayList<>();
        int i;
        for (i = 0; i < user_chats.size(); ++i) {
            ChatRelation cr = (ChatRelation) user_chats.get(i);
            userIds.add(cr.getOtherUserId());
        }
  */
        Collections.sort(user_chats, new CustomComparator());  //Ahora tengo ordenados los user_chats por orden alfabetico

   /*     Collections.sort(userIds, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });  */
        //Users ordenados, ahora hay q relacionarlo con los id's del chat

     /*   final ArrayList chatIds = new ArrayList();
        for (int k = 0; k < userIds.size(); ++k) {
            int index = user_chats.indexOf(userIds.get(k)); //Encontramos en que posición del array original estaba
            chatIds.add(user_chats.get(index));  //guardamos los ids en el mismo orden que los users
        }  */
        //Tenemos ordenados los ids que vamos a buscar, esto está hecho así para que se corresponda con el orden en el que nos los devolverá
        //back, así nos ahorramos el coste de asociar los ids con los resultados que obtenemos en back
        String URL = "https://whip-api.herokuapp.com/users/profile/list?";
    /*    if (userIds.size() > 0) URL = URL + "id=" + userIds.get(0);
        for (int j = 1; j < userIds.size(); ++j) {
            URL += "&id=" + userIds.get(j);
        }  */
        ChatRelation cr;
        final ArrayList<String> crNoUser = new ArrayList<>();
        if (user_chats.size() > 0 ) {
            cr = (ChatRelation) user_chats.get(0);
            if (!cr.getOtherUserId().equals("null")) {
                URL = URL + "id=" + cr.getOtherUserId();
            }
            else crNoUser.add(cr.getId());
            for (int j = 1; j < user_chats.size(); ++j) {
                cr = (ChatRelation) user_chats.get(j);
                URL += "&id=" + cr.getOtherUserId();
                if(cr.getOtherUserId().equals("null")) crNoUser.add(cr.getId());
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
                            for(int j = 0; j < crNoUser.size();++j){
                                userInfoForChat.add(new ChatRelation("Other User Deleted chat", "", crNoUser.get(j) ));
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
