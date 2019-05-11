package com.bernal.jonatan.whip.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

public class LostList extends AppCompatActivity {


    private String URL, URL_filtre;
    private RequestQueue requestqueue;
    private JSONArray resultat;
    private ArrayList<Post> Posts_perdidos;
    private PostAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contenedor;


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    private Spinner spinnerFiltre;
    String selectedItem = "";
    private TextView orderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lost);

        contenedor = findViewById(R.id.contenedor);
        spinnerFiltre = findViewById(R.id.spinner_filter_lost);
        orderBy = findViewById(R.id.orderby_lost);
        selectedItem = "";
        orderBy.setText(getString(R.string.order_by_cat));

        //Recarregar la pàgina
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutt);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });


        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/lostposts";
        URL_filtre = "https://whip-api.herokuapp.com/contributions/lostposts?sort=";

        requestqueue = Volley.newRequestQueue(this);

        Toolbar tool = findViewById(R.id.toolbar_listadoPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("LOST");

        Toast.makeText(getApplicationContext(), ul.getAPI_KEY(), Toast.LENGTH_SHORT).show();


        String[] itemsSort = new String[]{"", "Recent", "Dog", "Cat", "Other"};
        ArrayAdapter<String> adapterSort = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsSort);

        spinnerFiltre.setAdapter(adapterSort);


        spinnerFiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = spinnerFiltre.getSelectedItem().toString();
                if (!selectedItem.equals("")) {
                    URL_filtre = URL_filtre + selectedItem;

                    orderBy.setText((getString(R.string.order_by_cat)) + " " + selectedItem);
                    //TODO: enviar a la funcio

                    backFiltres();

                }

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (selectedItem.equals("")) back_normal();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_añadir:
                startActivity(new Intent(LostList.this, NewPostLost.class));
                break;
        }
        return true;
    }

    private void back_normal() {
        //TODO: spinnerFiltre.getSelectedItem().toString()
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Listado mostrado correctamente", Toast.LENGTH_SHORT).show();
                            resultat = response;
                            Posts_perdidos = new ArrayList<>();
                            LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            JSONObject postite;
                            for (int i = 0; i < resultat.length(); i++) {
                                postite = resultat.getJSONObject(i);
                                Posts_perdidos.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), "LOST"));
                            }
                            adapt = new PostAdapter(Posts_perdidos, "Lost");
                            contenedor.setAdapter(adapt);
                            contenedor.setLayoutManager(layout);
                            adapt.setOnListListener(new OnListListener() {
                                @Override
                                public void onPostClicked(int position, View vista) {
                                    String id_post = Posts_perdidos.get(contenedor.getChildAdapterPosition(vista)).getId();
                                    Intent i = new Intent(LostList.this, InfoPostLost.class);
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
                        Toast.makeText(getApplicationContext(), "ERROOOOOOOR", Toast.LENGTH_SHORT).show();

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


    }

    private void backFiltres() {
        JsonArrayRequest arrayJsonrequest = new JsonArrayRequest(
                JsonRequest.Method.GET,
                URL_filtre,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Toast.makeText(getApplicationContext(), "LLISTAT FILTRES MOSTRAT GOOD", Toast.LENGTH_SHORT).show();
                            resultat = response;
                            Posts_perdidos = new ArrayList<>();
                            LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                            layout.setOrientation(LinearLayoutManager.VERTICAL);
                            JSONObject postite;
                            for (int i = 0; i < resultat.length(); i++) {
                                postite = resultat.getJSONObject(i);
                                Posts_perdidos.add(new Post(postite.getString("id"), postite.getString("title"), postite.getString("photo_url_1"), postite.getString("text"), "LOST"));
                            }
                            adapt = new PostAdapter(Posts_perdidos, "Lost");
                            contenedor.setAdapter(adapt);
                            contenedor.setLayoutManager(layout);
                            adapt.setOnListListener(new OnListListener() {
                                @Override
                                public void onPostClicked(int position, View vista) {
                                    String id_post = Posts_perdidos.get(contenedor.getChildAdapterPosition(vista)).getId();
                                    Intent i = new Intent(LostList.this, InfoPostLost.class);
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
                        Toast.makeText(getApplicationContext(), "FILTRES: PROBLEMS MOSTRAR LLIST", Toast.LENGTH_SHORT).show();

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

    }


}