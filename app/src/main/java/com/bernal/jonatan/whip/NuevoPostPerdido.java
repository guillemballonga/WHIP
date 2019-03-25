package com.bernal.jonatan.whip;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NuevoPostPerdido extends AppCompatActivity {

    ImageView foto;
    Spinner especie,tipo;
    Button create,cancel;
    EditText titulo,cp,raza;

    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_post_perdido);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_nuevoPostPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("ABANDONO O PÉRDIDA");

        foto = (ImageView) findViewById(R.id.perfil_perroPerd);

        especie = (Spinner) findViewById(R.id.especie_postPerd);
        tipo = (Spinner) findViewById(R.id.tipo_postPerd);

        titulo = (EditText) findViewById(R.id.titulo_postPerd);
        cp = (EditText) findViewById(R.id.cp_postPerd);
        raza = (EditText) findViewById(R.id.raza_postPerd);


        //Botons

        create = (Button) findViewById(R.id.boton_create);
        cancel = (Button) findViewById(R.id.boton_cancelNewPostPerd);

        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(titulo.getText().toString().equals("") || cp.getText().toString().equals("") || raza.getText().toString().equals("") || especie.getSelectedItem().toString().equals("") || tipo.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Los campos con * son obligatorios",Toast.LENGTH_SHORT).show();

                }
                else {
                    //Guardar los datos del formulario en BACK. NOTA: No olvidar guardar la fecha de creación del Post


                    //Ir a ver el post en concreto
                    Toast.makeText(getApplicationContext(),"Post guardado correctamente",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NuevoPostPerdido.this, InfoPost.class));
                    finish();

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });




        // Spinner per a seleccionar els items
        String[] itemsEspecie = new String[]{"Perro*", "Gato*", "Otro*"};
        String[] itemsTipo = new String[]{"Abandono*", "Pérdida*"};

        ArrayAdapter<String> adapterEspecie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsEspecie);
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTipo);

        especie.setAdapter(adapterEspecie);
        tipo.setAdapter(adapterTipo);


        //Obrir la galeria d'imatges
        foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        //IMGUR
        fetchData();

    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery,"Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            foto.setImageURI(path);

            //Guardar el path de la foto en IMGUR

            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .header("Authorization", "b85b2517d6df7fb")
                    .header("User-Agent", "WHIP")
                    .build();
        }
    }


    private void fetchData() {
        httpClient = new OkHttpClient.Builder().build();
    }



}
