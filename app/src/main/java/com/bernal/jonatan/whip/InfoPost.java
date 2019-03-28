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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoPost extends AppCompatActivity {

    TextView titulo,fecha,especie,tipo,raza,contenido;
    ImageView foto_post, foto_user;
    String Identificador;

    private String URL;
    private RequestQueue requestqueue;

    private Usuari_Logejat ul = Usuari_Logejat.getUsuariLogejat("");

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


        //Recoger los datos de Back y cargarlos en la vista
        URL = "https://whip-api.herokuapp.com/contributions/lostposts/"+Identificador;
        requestqueue = Volley.newRequestQueue(this);

        
        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject lostpost = response.getJSONObject("lostpost");
                            titulo.setText(lostpost.getString("title"));
                            String[] data = (lostpost.getString("createdAt")).split("T");
                            fecha.setText(data[0]);
                            especie.setText(lostpost.getString("specie"));
                            if (lostpost.getString("type").equals("F")) {
                                tipo.setText("Encontrado");
                            }
                            else  tipo.setText("Pérdida");

                            raza.setText(lostpost.getString("race"));
                            contenido.setText(lostpost.getString("text"));
                            //Fotografías con IMGUR
                            foto_user.setBackgroundResource(R.drawable.icono_usuario);
                            foto_post.setBackgroundResource(R.drawable.perfilperro);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"ERROOOOOOOR",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                return params;
            }
        };
        requestqueue.add(objectJsonrequest);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_infopostperd,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.icono_fav:
                //comunicacion con back + cambiar color de la estrella


                Toast.makeText(getApplicationContext(),"Funciona estrellita",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
