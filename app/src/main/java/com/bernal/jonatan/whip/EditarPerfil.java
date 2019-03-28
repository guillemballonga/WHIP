package com.bernal.jonatan.whip;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditarPerfil extends AppCompatActivity {


    Button goToMostrarPerfilGuardant, goToMostrarPerfilCancelar;
    ImageView fotoperfil;
    EditText nom,cognom,user,cp;

    //variables para comucicación back
    private String URL;
    private RequestQueue requestqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        goToMostrarPerfilGuardant = (Button) findViewById(R.id.boto_guardar);
        goToMostrarPerfilCancelar = (Button) findViewById(R.id.boto_cancelar);

        nom = (EditText) findViewById(R.id.escr_nom);
        cognom = (EditText) findViewById(R.id.escr_cognom);
        user = (EditText) findViewById(R.id.escr_user);
        cp = (EditText) findViewById(R.id.escr_CP);

        fotoperfil = (ImageView) findViewById(R.id.imagen_perfil);



        //Gestión de las Toolbars
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar_editarPerfil);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("PERFIL");

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/users/profile";
        requestqueue = Volley.newRequestQueue(this);



        goToMostrarPerfilGuardant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comunicacion con Back
                //jason para comunicación con back
                JSONObject perfil_editat = new JSONObject();


                //JASON
                try {
                    perfil_editat.put("post_code", cp.getText().toString());
                    perfil_editat.put("name", nom.getText().toString() );
                    perfil_editat.put("about","");
                    perfil_editat.put("fam_name", cognom.getText().toString());
                    perfil_editat.put("username", user.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(nom.getText().toString().equals("") || cp.getText().toString().equals("") || cognom.getText().toString().equals("") || user.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Guardar los datos del formulario en BACK. NOTA: No olvidar guardar la fecha de creación del Post
                    JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                            JsonRequest.Method.PATCH,
                            URL,
                            perfil_editat,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(),"Perfil actualizado con éxito",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditarPerfil.this, MostrarPerfil.class));
                                    finish();
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
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", "2C4T55N-4SY40G3-JBG7QMB-4PYNJ9P"); //valor de V ha de ser el de la var global
                            return params;
                        }
                    };
                    requestqueue.add(objectJsonrequest);
//JASON



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
                startActivity(new Intent(EditarPerfil.this, MostrarPerfil.class));
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

        }
    }

}
