package com.bernal.jonatan.whip;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MostrarPerfil extends AppCompatActivity {

    Button goToEditarPerfil, goToMisPosts;
    TextView nom, correu, CP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_perfil);

        goToEditarPerfil = (Button) findViewById(R.id.boto_editar_perfil);
        goToMisPosts = (Button) findViewById(R.id.boto_mis_posts);

        nom = (TextView) findViewById(R.id.escr_nom);
        correu = (TextView) findViewById(R.id.escr_correu);
        CP = (TextView) findViewById(R.id.escr_CP);

        setCampsPerfil();



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

    private void setCampsPerfil() {
        //Posar parametres que t'envia back
       // nom.setText();
        //correu.setText();
        //CP.setText();

    }


}
