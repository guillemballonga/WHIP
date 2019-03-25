package com.bernal.jonatan.whip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoPost extends AppCompatActivity {

    TextView titulo,fecha,especie,tipo,raza,contenido;
    ImageView foto_post, foto_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post);

        titulo = (TextView) findViewById(R.id.titulo_postPerd);
        fecha = (TextView) findViewById(R.id.fecha_postPerd);
        especie = (TextView) findViewById(R.id.especie_postPerd);
        tipo = (TextView) findViewById(R.id.tipo_postPerd);
        raza = (TextView) findViewById(R.id.raza_postPerd);
        contenido = (TextView) findViewById(R.id.contenido_postPerd);

        foto_post = (ImageView) findViewById(R.id.foto_postPerd);
        foto_user = (ImageView) findViewById(R.id.imagen_coment_user);


        //Recoger los datos de Back y cargarlos en la vista


        //Harcoded
        titulo.setText("Toby-Perdido");
        fecha.setText("15/20/1999");
        especie.setText("Perro");
        tipo.setText("ABANDONO");
        raza.setText("Hasky de las nieves");
        contenido.setText("Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen");
        foto_user.setBackgroundResource(R.drawable.icono_usuario);
        foto_post.setBackgroundResource(R.drawable.perfilperro);

    }
}
