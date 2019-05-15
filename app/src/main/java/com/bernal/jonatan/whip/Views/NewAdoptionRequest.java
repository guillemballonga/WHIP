package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bernal.jonatan.whip.R;

import java.util.Objects;

public class NewAdoptionRequest extends AppCompatActivity {

    Button toQuedada;
    static String UsernameFromPost;
    static String AdoptionPostID;
    String textRequest;
    String photo_url;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        toQuedada = findViewById(R.id.boton_enviar_quedada);
        toQuedada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAdoptionRequest.this, NewQuedada.class));
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_solicitud_adopcion);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_nova_solicitud_adopt);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("SOLICITUD DE ADOPCIÓN");
    }

    public static void setAdoptionPostID(String id){
        AdoptionPostID = id;
    }

    public static void setUsernameFromPost(String username){
        UsernameFromPost=username;
    }





}
