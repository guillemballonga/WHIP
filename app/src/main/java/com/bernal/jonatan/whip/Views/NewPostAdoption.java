package com.bernal.jonatan.whip.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class NewPostAdoption extends AppCompatActivity implements ConcretePostPresenter.View {

    ConcretePostPresenter concretePostPresenter = new ConcretePostPresenter(this);

    @SuppressLint("StaticFieldLeak")
    static ImageView foto;
    Spinner especie;
    Button create, cancel;
    EditText titulo, cp, raza, contenido;

    //variables para comucicación back
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nou_post_adopcio);

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/adoptionposts";

        //Gestión de toolbar
        Toolbar tool = findViewById(R.id.toolbar_nuevoPostAdopcio);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("ADOPCIÓN");

        foto = findViewById(R.id.perfil_perroAdopcio);

        especie = findViewById(R.id.especie_postAdopcio);


        titulo = findViewById(R.id.titulo_postAdopcio);
        cp = findViewById(R.id.cp_postAdopcio);
        raza = findViewById(R.id.razaPerroAdopcio);
        contenido = findViewById(R.id.descripcion_postAdopcio);


        // Spinner per a seleccionar els items
        String[] itemsEspecie = new String[]{"Dog", "Cat", "Other"};

        ArrayAdapter<String> adapterEspecie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsEspecie);

        especie.setAdapter(adapterEspecie);


        //OBRIR IMATGES
        foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewPostAdoption.this, UploadImageFirebase.class);
                i.putExtra("idActivity", "adoption");
                startActivity(i);

            }

        });


        //Botons

        create = findViewById(R.id.boton_create_adopt);
        cancel = findViewById(R.id.boton_cancelNewPostAdopcio);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (titulo.getText().toString().equals("") || cp.getText().toString().equals("") || raza.getText().toString().equals("") || especie.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Los campos con * son obligatorios", Toast.LENGTH_SHORT).show();

                } else {
                    concretePostPresenter.createPost(URL, especie.getSelectedItem().toString(), UploadImageFirebase.getIdentificadorImatge(), raza.getText().toString(), cp.getText().toString(), contenido.getText().toString(), titulo.getText().toString(), "", "Adoption");
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
    public void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type, String username, String coord1, String coord2) {

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
        Intent i = new Intent(NewPostAdoption.this, InfoPostAdoption.class);
        i.putExtra("identificadorPost", id);
        startActivity(i);
        finish();

    }
}
