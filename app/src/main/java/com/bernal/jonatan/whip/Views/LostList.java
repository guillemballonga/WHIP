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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.Presenters.PostPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.OnListListener;
import com.bernal.jonatan.whip.RecyclerViews.PostAdapter;

import java.util.ArrayList;

public class LostList extends AppCompatActivity implements PostPresenter.View {


    PostPresenter postPresenter = new PostPresenter(this);
    private String URL, URL_filtre, URL_filtre_final;
    private PostAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    EditText textBuscarPostLost;
    RecyclerView contenedor;
    Button botoBuscar;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private Spinner spinnerFiltre;
    String selectedItem = "";
    private TextView orderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lost);

        textBuscarPostLost=findViewById(R.id.text_buscar_post_lost);
        botoBuscar=findViewById(R.id.boto_buscar_post_lost);
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

        Toolbar tool = findViewById(R.id.toolbar_listadoPerd);
        setSupportActionBar(tool);

        getSupportActionBar().setTitle(R.string.p_rdida);
        String[] itemsSort = new String[]{"",  getString(R.string.recent), getString(R.string.dog), getString(R.string.cat), getString(R.string.other)};
        ArrayAdapter<String> adapterSort = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsSort);

        spinnerFiltre.setAdapter(adapterSort);

        spinnerFiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = spinnerFiltre.getSelectedItem().toString();
                if (!selectedItem.equals("")) {
                    switch (selectedItem) {
                        case "Perro":
                            URL_filtre_final = URL_filtre + "Dog";
                            break;
                        case "Gato":
                            URL_filtre_final = URL_filtre + "Cat";
                            break;
                        case "Otro":
                            URL_filtre_final = URL_filtre + "Other";
                            break;
                        default:
                            URL_filtre_final = URL_filtre + selectedItem;

                    }
                    orderBy.setText((getString(R.string.order_by_cat)) + " " + selectedItem);
                    backFiltres();
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (selectedItem.equals("")) back_normal();

        botoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textBuscarPostLost.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.mensajeBusquedaVacio), Toast.LENGTH_SHORT).show();
                } else {
                    postPresenter.searchPost(textBuscarPostLost.getText().toString(), URL);
                }
            }
        });

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
        postPresenter.getLostPosts(URL);
    }

    private void backFiltres() {
        postPresenter.getLostPosts(URL_filtre_final);
    }

    @Override
    public void chargeAdoptionList(ArrayList posts) {

    }

    @Override
    public void chargeLostList(final ArrayList posts) {
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new PostAdapter(posts, "Lost");
        contenedor.setAdapter(adapt);
        contenedor.setLayoutManager(layout);
        adapt.setOnListListener(new OnListListener() {
            @Override
            public void onPostClicked(int position, View vista) {
                Post post = (Post) posts.get(contenedor.getChildAdapterPosition(vista));
                String id_post = post.getId();
                Intent i = new Intent(LostList.this, InfoPostLost.class);
                i.putExtra("identificadorPost", id_post);
                startActivity(i);
            }
        });
    }
}
