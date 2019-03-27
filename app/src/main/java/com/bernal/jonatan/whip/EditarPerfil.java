package com.bernal.jonatan.whip;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Request;

public class EditarPerfil extends AppCompatActivity {


    Button goToMostrarPerfilGuardant, goToMostrarPerfilCancelar;
    ImageView fotoperfil;
    EditText nom, correu, CP;
    TextView nomTV, correuTV, CPTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        goToMostrarPerfilGuardant = (Button) findViewById(R.id.boto_guardar);
        goToMostrarPerfilCancelar = (Button) findViewById(R.id.boto_cancelar);

        fotoperfil = (ImageView) findViewById(R.id.imagen_perfil);

        goToMostrarPerfilGuardant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = (EditText) findViewById(R.id.escr_nom);
                correu = (EditText) findViewById(R.id.escr_correu);
                CP = (EditText) findViewById(R.id.escr_CP);
                if(nom.getText().toString().equals("") || correu.getText().toString().equals("") || CP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Hay algun campo vacío, rellenalo para modificar la información de tu perfil",Toast.LENGTH_SHORT).show();
                }
                else {
                    // startActivity(new Intent(EditarPerfil.this, MostrarPerfil.class));
                    setNousParametres();
                    Toast.makeText(getApplicationContext(),"Perfil Modificado con Éxito",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            
        });

        fotoperfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                obrirgaleria();
            }
        });

        goToMostrarPerfilCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void obrirgaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery,"Seleccione la Aplicación"),10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            fotoperfil.setImageURI(path);

            //Guardar el path de la foto en IMGUR


            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .header("Authorization", "b85b2517d6df7fb")
                    .header("User-Agent", "WHIP")
                    .build();
        }
    }


    private void setNousParametres() {

        //enviar contingut dels nous parametres a back
    }


}
