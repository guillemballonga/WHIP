package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ListadoPerdida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_perdida);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_listadoPerd);
        setSupportActionBar(tool);

        ArrayList<Fuente> Posts_perdidos = new ArrayList<Fuente>();

        //Posts harcodeados
        Posts_perdidos.add(new Fuente("Toby perdido1", R.drawable.perro,"He perdido a mi perro por la zona de Barcelona. Blablablablablabalbalablabal...",0));
        Posts_perdidos.add(new Fuente("Gatito perdido2", R.drawable.gatito,"He perdido a mi gatito por la zona de Barcelona. Blablablablablabalbalablabal...",0));
        Posts_perdidos.add(new Fuente("Toby perdido3", R.drawable.perro,"He perdido a mi perro por la zona de Barcelona. Blablablablablabalbalablabal...",0));
        Posts_perdidos.add(new Fuente("Gatito perdido4", R.drawable.gatito,"He perdido a mi gatito por la zona de Barcelona. Blablablablablabalbalablabal...",0));
        Posts_perdidos.add(new Fuente("Toby perdido5", R.drawable.perro,"He perdido a mi perro por la zona de Barcelona. Blablablablablabalbalablabal...",0));

        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        contenedor.setAdapter(new Adaptador(Posts_perdidos));
        contenedor.setLayoutManager(layout);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.icono_añadir:
                startActivity(new Intent(ListadoPerdida.this, NuevoPostPerdido.class));
                break;
        }
        return true;
    }



}
