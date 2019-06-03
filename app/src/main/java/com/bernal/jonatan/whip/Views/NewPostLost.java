package com.bernal.jonatan.whip.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class NewPostLost extends AppCompatActivity implements ConcretePostPresenter.View {

    ConcretePostPresenter concretePostPresenter = new ConcretePostPresenter(this);
    @SuppressLint("StaticFieldLeak")
    static ImageView foto;
    Spinner especie, tipo;
    Button create, cancel;
    EditText titulo, cp, raza, contenido;

    //variables para comucicación back
    private String URL;
    private RequestQueue requestqueue;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_post_perdido);

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/lostposts";
        requestqueue = Volley.newRequestQueue(this);

        //Gestión de toolbar
        Toolbar tool = findViewById(R.id.toolbar_nuevoPostPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(R.string.p_rdida);

        foto = findViewById(R.id.perfil_perroPerd);

        especie = findViewById(R.id.especie_postPerd);
        tipo = findViewById(R.id.tipo_postPerd);

        titulo = findViewById(R.id.titulo_postPerd);
        cp = findViewById(R.id.cp_postPerd);
        raza = findViewById(R.id.raza_postPerd);
        contenido = findViewById(R.id.descripcion_postPerd);


        // Spinner per a seleccionar els items
        String[] itemsEspecie = new String[]{getString(R.string.dog), getString(R.string.cat), getString(R.string.other)};
        String[] itemsTipo = new String[]{getString(R.string.found), getString(R.string.lost)};

        ArrayAdapter<String> adapterEspecie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsEspecie);
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTipo);

        especie.setAdapter(adapterEspecie);
        tipo.setAdapter(adapterTipo);


        //Obrir la galeria d'imatges
        foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //openGallery();

                //Quan cliqui obrir UploadImagesFirebase
                // startActivity(new Intent(NewPostLost.this, UploadImageFirebase.class));

                Intent i = new Intent(NewPostLost.this, UploadImageFirebase.class);
                i.putExtra("idActivity", "lost");
                //i.putExtra("idImageView");
                startActivity(i);


            }
        });


        //Botons

        create = findViewById(R.id.boton_create);
        cancel = findViewById(R.id.boton_cancelNewPostPerd);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (titulo.getText().toString().equals("") || cp.getText().toString().equals("") || raza.getText().toString().equals("") || especie.getSelectedItem().toString().equals("") || tipo.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.required_fields, Toast.LENGTH_SHORT).show();

                } else {
                    concretePostPresenter.createPost(URL, especie.getSelectedItem().toString(), UploadImageFirebase.getIdentificadorImatge(), raza.getText().toString(), cp.getText().toString(), contenido.getText().toString(), titulo.getText().toString(), tipo.getSelectedItem().toString(), "Lost");
                }



                }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            Uri path = data.getData();
            foto.setImageURI(path);
        }
    }

    public static void retrieveImage(String idImageFirebase) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //TODO: necessito recuperar l objecte desde el json. a child posarhi l indetificador guardat
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);

        // imageView = (ImageView) findViewById(R.id.imageFirebase);
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    foto.setImageBitmap(bitmap);

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
    public void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type, String username) {

    }

    @Override
    public void setFavorite(Boolean fav) {

    }

    @Override
    public void setDeletePost() {

    }

    @Override
    public void recharge() {
        recreate();
    }

    @Override
    public void notifyCreate(String id) {
        Intent i = new Intent(NewPostLost.this, InfoPostLost.class);
        i.putExtra("identificadorPost", id);
        startActivity(i);
        finish();
    }
}