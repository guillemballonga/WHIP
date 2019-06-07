package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.Presenters.UserPresenter;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.master.glideimageview.GlideImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserPresenter.View {


    Button lost, adoption, events;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ImageView idioma_es,idioma_en;

    static GlideImageView imatge;
    TextView nom, user;

    String urlFoto, n, u;

    UserPresenter userPresenter = new UserPresenter(this);


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
        getSupportActionBar().setTitle(R.string.main_menu);

        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);

        userPresenter.getUser();

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
                MainActivity.doLogOut(1);
                startActivity(new Intent(MainMenu.this, MainActivity.class));
                finish();
                break;
        }
        return false;
    }

    public static void retrieveImage(String idImageFirebase) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imatge.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException ignored) {
        }

    }

    @Override
    public void getUserInfo(String cpt, String email, String family_name, String first_name, String photoURL, String username) {
        if (username.equals("null")) username = "";
        n = first_name;
        u = username;
        urlFoto = photoURL;
        carregaParametresBarraMenu();
    }

    public void carregaParametresBarraMenu() {
        NavigationView nv = findViewById(R.id.navigation_view);
        View hView = nv.getHeaderView(0);

        //PARÀMETRES DEL NAVIGATION
        //--------------------------------------------------------------------------------

        nom = hView.findViewById(R.id.nom_real_barra_lateral);
        user = hView.findViewById(R.id.user_barra_lateral);
        imatge = hView.findViewById(R.id.imagen_perfil_barra_lateral);

        nom.setText(n);
        user.setText(u);
        if (urlFoto.equals("") || urlFoto.equals("null")) {

        } else if (urlFoto.substring(1, 7).equals("images")) {
            retrieveImage(urlFoto);
        } else { //CARREGAR IMATGE DE GOOGLE
            imatge.loadImageUrl(urlFoto);
        }


        //----------------------------------------------------------------------------------

        nv.setNavigationItemSelectedListener(this);
    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setUserPosts(ArrayList mis_posts) {

    }

    @Override
    public void sendInfoForChat(ArrayList userInfoForChat) {

    }
}
