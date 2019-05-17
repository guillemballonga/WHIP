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
import com.bernal.jonatan.whip.Presenters.PostPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.OnListListener;
import com.bernal.jonatan.whip.RecyclerViews.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdoptionList extends AppCompatActivity implements PostPresenter.View {

    PostPresenter postPresenter = new PostPresenter(this);
    private String URL, URL_filtre;
    private PostAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contenedor_adopt;


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    private Spinner spinnerFiltre;
    private TextView orderBy;
    private String selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adoption);


        contenedor_adopt = findViewById(R.id.contenedor_adoption);
        spinnerFiltre = findViewById(R.id.spinner_filter_adoption);
        orderBy = findViewById(R.id.orderby_adop);
        selectedItem = "";
        orderBy.setText(getString(R.string.order_by_cat));

        //Recarregar la pàgina
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });


        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/adoptionposts";
        URL_filtre = "https://whip-api.herokuapp.com/contributions/adoptionposts?sort=";

        Toolbar tool = findViewById(R.id.toolbar_listadoAdopt);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("ADOPCIÓN");

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

        //Llamada a la API

        postPresenter.getAdoptionPosts(URL); //hagamos sobre URL lo de los filtros y trabajamos sobre URL siempre, así nos ahorramos la repetcion
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_añadir:
                startActivity(new Intent(AdoptionList.this, NewPostAdoption.class));
                break;
        }
        return true;
    }

    private void backFiltres() {
        postPresenter.getAdoptionPosts(URL_filtre);
    }

    @Override
    public void chargeAdoptionList(final ArrayList posts) {
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new PostAdapter(posts, "Adoption");
        contenedor_adopt.setAdapter(adapt);
        contenedor_adopt.setLayoutManager(layout);
        adapt.setOnListListener(new OnListListener() {
            @Override
            public void onPostClicked(int position, View vista) {
                String id_post = ((Post)posts.get(contenedor_adopt.getChildAdapterPosition(vista))).getId();
                Intent i = new Intent(AdoptionList.this, InfoPostAdoption.class);
                i.putExtra("identificadorPost", id_post);
                startActivity(i);
            }
        });
    }

    @Override
    public void chargeLostList(ArrayList posts) {

    }
}



