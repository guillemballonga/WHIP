package com.bernal.jonatan.whip;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MostrarPerfil extends AppCompatActivity {

    Button goToEditarPerfil, goToMisPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_perfil);

        goToEditarPerfil = (Button) findViewById(R.id.boto_editar_perfil);
        goToMisPosts = (Button) findViewById(R.id.boto_mis_posts);

        //Gestión de la toolbar
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_mostrarPerfil);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("PERFIL");






        goToEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, EditarPerfil.class));
                //finish();
            }
        });

        goToMisPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, ListadoPerdida.class));
                //finish();
            }
        });
    }



}
