package com.bernal.jonatan.whip.Views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Presenters.UserPresenter;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.master.glideimageview.GlideImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MostrarPerfil extends AppCompatActivity implements UserPresenter.View {


    UserPresenter userPresenter;

    static String nomBack;
    static String cognomBack;
    static String userBack;
    static String cpBack;
    static String correuBack;
    static String urlFoto;
    Button goToEditarPerfil, goToMisPosts;
    TextView nom, cognom, user, cp, correu;
    //ImageView imatge;
    GlideImageView imatge;

    //Objectes per JSONGet
    private String URL;
    private RequestQueue requestqueue;
    private JSONArray resultat;
    private JSONObject result;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = ul.getAPI_KEY();

    public static String getCorreu() {
        return correuBack;
    }

    public static String getNom() {
        return nomBack;
    }

    public static String getCognom() {
        return cognomBack;
    }

    public static String getUsername() {
        return userBack;
    }

    public static String getCP() {
        return cpBack;
    }

    public static String getFoto() {
        return urlFoto;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_perfil);
        goToEditarPerfil = findViewById(R.id.boto_editar_perfil);
        goToMisPosts = findViewById(R.id.boto_mis_posts);
        UserPresenter userPresenter = new UserPresenter(this);
        nom = findViewById(R.id.escr_nom);
        cognom = findViewById(R.id.escr_cognom);
        user = findViewById(R.id.escr_username);
        cp = findViewById(R.id.escr_CP);
        correu = findViewById(R.id.escr_correu);
        correu.setTextSize(12);
        imatge = findViewById(R.id.imagen_perfil);


        //Llamo al presenter, le paso lo q necesita, en el model hago la llamada a la API

        userPresenter.getUser();

        //Gestión de la toolbar
        Toolbar tool = findViewById(R.id.toolbar_mostrarPerfil);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("PERFIL");

        goToEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, EditProfile.class));
                finish();
            }
        });

        goToMisPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, MyPosts.class));
                //finish();
            }
        });
    }

    public void retrieveImage(String idImageFirebase) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Tot: necessito recuperar l objecte desde el json. a child posarhi l indetificador guardat
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);

        //foto_post = (ImageView) findViewById(R.id.foto_postPerd);
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
        if(username.toString().equals("null")) username="";
        if(cpt.toString().equals("null")) cpt="";
        nom.setText(first_name);
        cognom.setText(family_name);
        user.setText(username);
        cp.setText(cpt);
        correu.setText(email);
        urlFoto = photoURL;
        if (urlFoto.equals("") || urlFoto.equals("null")) {

        } else if (photoURL.substring(1, 7).equals("images")) {
            retrieveImage(photoURL);
        } else  { //CARREGAR IMATGE DE GOOGLE
            imatge.loadImageUrl(photoURL);
        }
    }

    @Override
    public void changeActivity() {
        //Nothing to do
    }

    @Override
    public void setUserPosts(ArrayList mis_posts) {

    }
}