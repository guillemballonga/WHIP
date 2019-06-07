package com.bernal.jonatan.whip.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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


public class EditProfile extends AppCompatActivity implements UserPresenter.View {


    Button goToMostrarPerfilGuardant, goToMostrarPerfilCancelar;
    @SuppressLint("StaticFieldLeak")
    static GlideImageView fotoperfil;
    EditText nom, cognom, user, cp;
    TextView correu;

    UserPresenter userPresenter = new UserPresenter(this);
    //variables para comucicación back
    private String URL;
    private String urlFoto = ""; //la que agafo si canvio de foto
    private String urlBD = MostrarPerfil.getFoto();

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    private Uri path;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        goToMostrarPerfilGuardant = findViewById(R.id.boto_guardar);
        goToMostrarPerfilCancelar = findViewById(R.id.boto_cancelar);
        fotoperfil = findViewById(R.id.imagen_perfil);
        correu = findViewById(R.id.escr_correu);
        nom = findViewById(R.id.escr_nom);
        cognom = findViewById(R.id.escr_cognom);
        user = findViewById(R.id.escr_user);
        cp = findViewById(R.id.escr_CP);

        userPresenter.getUser();

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_editarPerfil);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.perfil);

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/users/profile";


        goToMostrarPerfilGuardant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UploadImageFirebase.getIdentificadorImatge() != null)
                    urlFoto = UploadImageFirebase.getIdentificadorImatge(); //si lhe canviat tindra valor, sino vindra buit

                if (urlFoto.equals("") || urlFoto.equals("null")) {

                    //si no l he canviat
                    urlFoto = urlBD;
                } else if (urlFoto.substring(1, 7).equals("images")) { // si l he canviat
                    urlBD = urlFoto;
                    //urlFoto = UploadImageFirebase.getIdentificadorImatge();
                }
                //else urlFoto = urlBD;
                if (!urlFoto.equals("") && !urlFoto.equals("null")) retrieveImage(urlFoto);


                if (nom.getText().toString().equals("") || cp.getText().toString().equals("") || cognom.getText().toString().equals("") || user.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                } else {
                    userPresenter.modifyUser(cp.getText().toString(), nom.getText().toString(), cognom.getText().toString(), user.getText().toString(), urlFoto);
                }
            }
        });

        fotoperfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Crida per obrir les fotos
                Intent i = new Intent(EditProfile.this, UploadImageFirebase.class);
                i.putExtra("idActivity", "edit");
                startActivity(i);

            }
        });

        goToMostrarPerfilCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, MostrarPerfil.class));
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            path = data.getData();
            fotoperfil.setImageURI(path);
        }
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
                    fotoperfil.setImageBitmap(bitmap);

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
        if (photoURL.equals("") || urlFoto.equals("null")) {

        } else if (photoURL.substring(1, 7).equals("images")) {
            retrieveImage(photoURL);
        } else { //CARREGAR IMATGE DE GOOGLE
            fotoperfil.loadImageUrl(urlFoto);
        }

    }

    @Override
    public void changeActivity() {
        startActivity(new Intent(EditProfile.this, MainMenu.class));
        finish();
    }

    @Override
    public void setUserPosts(ArrayList mis_posts) {

    }

    @Override
    public void sendInfoForChat(ArrayList userInfoForChat) {

    }
}