package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Favorite;
import com.bernal.jonatan.whip.Presenters.FavoritesController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoPost extends AppCompatActivity {

    TextView titulo, fecha, especie, tipo, raza, contenido;
    ImageView foto_post, foto_user;
    String Identificador;

    FavoritesController fc = new FavoritesController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post);

        //Obtengo el ID del post
        Identificador = getIntent().getStringExtra("identificadorPost");

        titulo = (TextView) findViewById(R.id.titulo_postPerd);
        fecha = (TextView) findViewById(R.id.fecha_postPerd);
        especie = (TextView) findViewById(R.id.especie_postPerd);
        tipo = (TextView) findViewById(R.id.tipo_postPerd);
        raza = (TextView) findViewById(R.id.raza_postPerd);
        contenido = (TextView) findViewById(R.id.contenido_postPerd);

        foto_post = (ImageView) findViewById(R.id.foto_postPerd);
        foto_user = (ImageView) findViewById(R.id.imagen_coment_user);

        //Gestión toolbar
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_infoPostPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("ENCONTRADO O PÉRDIDA");


        /*titulo.setText(lostpost.getString("title"));
        String[] data = (lostpost.getString("createdAt")).split("T");
        fecha.setText(data[0]);
        especie.setText(lostpost.getString("specie"));
        if (lostpost.getString("type").equals("F")) {
            tipo.setText("Encontrado");
        } else tipo.setText("Pérdida");

        raza.setText(lostpost.getString("race"));
        contenido.setText(lostpost.getString("text"));
        //Fotografías con IMGUR
        foto_user.setBackgroundResource(R.drawable.icono_usuario);
        foto_post.setBackgroundResource(R.drawable.perfilperro);*/


    }


    public boolean onCreateOptionsMenu(final Menu menu) {

        boolean f = fc.getFavorite(this,Identificador).isFav();
        if (f) {
            Toast.makeText(getApplicationContext(), "MENU FAVORITO", Toast.LENGTH_SHORT).show();
            getMenuInflater().inflate(R.menu.menu_infopostperdlike, menu);
        } else {
            Toast.makeText(getApplicationContext(), "MENU NO FAVORITO", Toast.LENGTH_SHORT).show();
            getMenuInflater().inflate(R.menu.menu_infopostperd, menu);
        }

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_fav_rell:
                //comunicacion con back + cambiar color de la estrella

                fc.setDislike(this,Identificador);
                finish();
                startActivity(getIntent());

                break;
            case R.id.icono_fav:

                fc.setLike(this,Identificador);
                finish();
                startActivity(getIntent());

                break;
        }
        return true;
    }


}
