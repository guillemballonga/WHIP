package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.OnListListener;
import com.bernal.jonatan.whip.RecyclerViews.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneFragmentMisPosts extends Fragment {
    View view;
    private String URL;
    private RequestQueue requestqueue;
    private JSONArray resultat;
    private ArrayList<Post> Mis_Posts;
    private PostAdapter adapt;
    RecyclerView contenedor;

    String tipo;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one_mis_posts, container, false);

        contenedor = view.findViewById(R.id.contenedor_misPosts);


        if (getArguments() != null && getArguments().getString("title").equals("Post Propios")) {
            URL = "https://whip-api.herokuapp.com/contributions/myposts";
        } else if (getArguments() != null && getArguments().getString("title").equals("Post Comentados")) {
            URL = "https://whip-api.herokuapp.com/contributions/commentedposts";
        } else if (getArguments() != null && getArguments().getString("title").equals("Post Favoritos")) {
            URL = "https://whip-api.herokuapp.com/contributions/favorites";
        }

        requestqueue = Volley.newRequestQueue(this.getContext());

        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            resultat = response;
                            Mis_Posts = new ArrayList<>();
                            LinearLayoutManager layout = new LinearLayoutManager(getActivity().getApplicationContext());
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            JSONObject postite;
                            for (int i = 0; i < resultat.length(); i++) {
                                postite = resultat.getJSONObject(i);
                                if (postite.has("type")) tipo = "LOST";
                                else tipo = "ADOPTION";
                                Mis_Posts.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), tipo));
                            }
                            adapt = new PostAdapter(Mis_Posts, "PostPropio");
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
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext().getApplicationContext(), "ERROOOOOOOR", Toast.LENGTH_SHORT).show();
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
        requestqueue.add(arrayJsonrequest);


        return view;
    }
}
