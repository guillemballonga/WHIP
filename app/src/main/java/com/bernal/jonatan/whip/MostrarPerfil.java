package com.bernal.jonatan.whip;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MostrarPerfil extends AppCompatActivity {

    Button goToEditarPerfil, goToMisPosts;
    String nomBack, cognomBack, userBack, cpBack, correuBack;
    TextView nom,cognom,user,cp, correu;

    //Objectes per JSONGet
    private String URL;
    private RequestQueue requestqueue;
    private JSONArray resultat;
    private JSONObject result;

    private Usuari_Logejat ul = Usuari_Logejat.getUsuariLogejat("");
    private String api = ul.getAPI_KEY();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_perfil);

        goToEditarPerfil = (Button) findViewById(R.id.boto_editar_perfil);
        goToMisPosts = (Button) findViewById(R.id.boto_mis_posts);

    //GET PER CONNEXIÓ AMB BACK
        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/users/profile";
        requestqueue = Volley.newRequestQueue(this);


        //Llamada a la API
        JsonObjectRequest arrayJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Info Carregada Correctament", Toast.LENGTH_SHORT).show();
                            result = response;

                            MostrarParametresPerfil(response);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization", api); //valor de V ha de ser el de la var global
                        return params;
                    }
                };
        requestqueue.add(arrayJsonrequest);
    //FINAL GET

        //Gestión de la toolbar
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_mostrarPerfil);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("PERFIL");

        goToEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, EditarPerfil.class));
                //finish();
            }
        });

        goToMisPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MostrarPerfil.this, ListadoPerdida.class));
                //finish();
            }
        });
    }
//Funció per assignar els parametres rebuts de back
    private void MostrarParametresPerfil(JSONObject response) throws JSONException {

        nomBack = result.getString("first_name");
        nom = (TextView) findViewById(R.id.escr_nom);
        nom.setText(nomBack);

        cognomBack = result.getString("family_name");
        cognom = (TextView) findViewById(R.id.escr_cognom);
        cognom.setText(cognomBack);

        userBack = result.getString("username");
        user = (TextView) findViewById(R.id.escr_username);
        user.setText(userBack);

        cpBack = result.getString("post_code");
        cp = (TextView) findViewById(R.id.escr_CP);
        cp.setText(cpBack);

        correuBack = result.getString("email");
        correu = (TextView) findViewById(R.id.escr_correu);
        correu.setText(correuBack);
        correu.setTextSize(12);

    }


}
