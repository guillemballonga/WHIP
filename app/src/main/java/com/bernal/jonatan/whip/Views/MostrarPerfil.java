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
import java.util.Objects;

public class MostrarPerfil extends AppCompatActivity implements UserPresenter.View {

    UserPresenter userPresenter = new UserPresenter(this);
    static String userBack;
    static String urlFoto;
    Button goToEditarPerfil, goToMisPosts, gotoMessages;
    TextView nom, cognom, user, cp, correu;
    GlideImageView imatge;

    public static String getUsername() {
        return userBack;
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
        gotoMessages = findViewById(R.id.boto_mis_mensajes);
        nom = findViewById(R.id.escr_nom);
        cognom = findViewById(R.id.escr_cognom);
        user = findViewById(R.id.escr_username);
        cp = findViewById(R.id.escr_CP);
        correu = findViewById(R.id.escr_correu);
        correu.setTextSize(12);
        imatge = findViewById(R.id.imagen_perfil);

        userPresenter.getUser();

        //Gestión de la toolbar
        Toolbar tool = findViewById(R.id.toolbar_mostrarPerfil);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.perfil);

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
            }
        });

        gotoMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MostrarPerfil.this, ChatList.class));
            }
        });
    }

    public void retrieveImage(String idImageFirebase) {

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
        if (cpt.equals("null")) cpt = "";
        nom.setText(first_name);
        cognom.setText(family_name);
        user.setText(username);
        cp.setText(cpt);
        correu.setText(email);
        urlFoto = photoURL;

        if (urlFoto.equals("") || urlFoto.equals("null")) {

        } else if (photoURL.substring(1, 7).equals("images")) {
            retrieveImage(photoURL);
        } else { //CARREGAR IMATGE DE GOOGLE
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

    @Override
    public void sendInfoForChat(ArrayList userInfoForChat) {

    }
}