package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bernal.jonatan.whip.R;

import java.util.Locale;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button lost, adoption, events;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ImageView idioma_es,idioma_en;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        lost = findViewById(R.id.boton_lost);
        adoption = findViewById(R.id.boton_adoption);
        events = findViewById(R.id.boton_eventos);

        idioma_en = findViewById(R.id.idioma_ingles);
        idioma_es = findViewById(R.id.idioma_espanol);

        Toolbar tool = findViewById(R.id.toolbar_menuPrincipal);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("MENÚ PRINCIPAL");

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);


        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, LostList.class));
            }
        });

        adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, AdoptionList.class));

            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, EventList.class));
            }
        });

        idioma_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale localizacion = new Locale("es", "ES");
                Locale.setDefault(localizacion);
                Configuration config = new Configuration();
                config.locale = localizacion;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
        });

        idioma_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale localizacion = new Locale("en", "EN");
                Locale.setDefault(localizacion);
                Configuration config = new Configuration();
                config.locale = localizacion;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_encontrado_perdido:
                startActivity(new Intent(MainMenu.this, LostList.class));
                break;
            case R.id.nav_adopcion:

                startActivity(new Intent(MainMenu.this, AdoptionList.class));
                break;
            case R.id.nav_VerPerfil:

                startActivity(new Intent(MainMenu.this, MostrarPerfil.class));
                break;
            case R.id.nav_eventos:
                startActivity(new Intent(MainMenu.this, EventList.class));
                break;
            case R.id.nav_logout:

                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_notify:
                //startActivity(new Intent(MainMenu.this, NewPostLost.class));
                break;
        }
        return true;
    }
}
