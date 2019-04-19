package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdoptionList extends AppCompatActivity {

    private String URL;
    private RequestQueue requestqueue;
    private JSONArray resultat;
    private ArrayList<Fuente> Posts_perdidos;
    private Adaptador adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contenedor_adopt;


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("");
    private String api = ul.getAPI_KEY();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adoption);


        contenedor_adopt =  findViewById(R.id.contenedor_adoption);

        //Recarregar la pàgina
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });


        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/adoptionposts";
        requestqueue = Volley.newRequestQueue(this);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_listadoAdopt);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("ADOPCIÓN");

        Toast.makeText(getApplicationContext(),ul.getAPI_KEY(),Toast.LENGTH_SHORT).show();

        //Llamada a la API

        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Toast.makeText(getApplicationContext(),"Listado mostrado correctamente",Toast.LENGTH_SHORT).show();
                            resultat = response;
                            Posts_perdidos = new ArrayList<>();
                            LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            JSONObject postite;
                            for (int i = 0; i < resultat.length();i++) {
                                postite = resultat.getJSONObject(i);
                                Posts_perdidos.add(new Fuente(postite.getString("id"),postite.getString("title"),postite.getString("photo_url_1"),postite.getString("text"),0));
                            }
                            adapt = new Adaptador(Posts_perdidos,"Adoption");
                            contenedor_adopt.setAdapter(adapt);
                            contenedor_adopt.setLayoutManager(layout);
                            adapt.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    String id_post = Posts_perdidos.get(contenedor_adopt.getChildAdapterPosition(view)).getId();
                                    Intent i = new Intent(AdoptionList.this, InfoPostAdoption.class);
                                    i.putExtra("identificadorPost",id_post);
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
                        Toast.makeText(getApplicationContext(),"ERROOOOOOOR",Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", api); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestqueue.add(arrayJsonrequest);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.icono_añadir:
                startActivity(new Intent(AdoptionList.this, NewPostAdoption.class));
                break;
        }
        return true;
    }



}



