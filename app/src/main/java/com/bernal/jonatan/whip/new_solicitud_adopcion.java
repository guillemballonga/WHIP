package com.bernal.jonatan.whip;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

public class new_solicitud_adopcion extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_solicitud_adopcion);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_nova_quedada);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("SOLICITUD DE ADOPCIÓN");
    }
}
