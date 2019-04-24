package com.bernal.jonatan.whip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.master.glideimageview.GlideImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditProfile extends AppCompatActivity {


    Button goToMostrarPerfilGuardant, goToMostrarPerfilCancelar;
    @SuppressLint("StaticFieldLeak")
    static GlideImageView fotoperfil;
    EditText nom, cognom, user, cp;
    TextView correu;


    //variables para comucicación back
    private String URL, urlFoto;
    private String urlBD = MostrarPerfil.getFoto();
    private RequestQueue requestqueue;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    private Uri path;

    public static void setVistaPreviaImatge(String identificadorImatge) {

        retrieveImage(identificadorImatge);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        goToMostrarPerfilGuardant = findViewById(R.id.boto_guardar);
        goToMostrarPerfilCancelar = findViewById(R.id.boto_cancelar);
        correu = findViewById(R.id.escr_correu);
        nom = findViewById(R.id.escr_nom);
        cognom = findViewById(R.id.escr_cognom);
        user = findViewById(R.id.escr_user);
        cp = findViewById(R.id.escr_CP);

        carregaParametres();

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_editarPerfil);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("PERFIL");

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
                    perfil_editat.put("name", nom.getText().toString());
                    perfil_editat.put("about", "hola");
                    perfil_editat.put("fam_name", cognom.getText().toString());
                    perfil_editat.put("username", user.getText().toString());


                    //aqui carrego la nova foto canviada
                    urlFoto = UploadImageFirebase.getIdentificadorImatge();
                    if (!urlFoto.equals("")) retrieveImage(urlFoto);
                    perfil_editat.put("photo_url", urlFoto);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (nom.getText().toString().equals("") || cp.getText().toString().equals("") || cognom.getText().toString().equals("") || user.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    //Guardar los datos del formulario en BACK. NOTA: No olvidar guardar la fecha de creación del Post
                    JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                            JsonRequest.Method.PATCH,
                            URL,
                            perfil_editat,

                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    startActivity(new Intent(EditProfile.this, MostrarPerfil.class));
                                    finish();
                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();


                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", api); //valor de V ha de ser el de la var global
                            return params;
                        }
                    };
                    requestqueue.add(objectJsonrequest);
//JASON


                }


                finish();
            }


        });

        fotoperfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //obrirgaleria();
                //guardar galeria
                //AQUI CRIDO PER OBRIR LES FOTOS
                //startActivity(new Intent(EditProfile.this, UploadImageFirebase.class));


                Intent i = new Intent(EditProfile.this, UploadImageFirebase.class);
                //i.putExtra("idImageView", R.id.perfil_perroPerd);
                //i.putExtra("idImageView");
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

    private void carregaParametres() {
        correu.setText(MostrarPerfil.getCorreu());
        correu.setTextSize(12);
        nom.setText(MostrarPerfil.getNom());
        cognom.setText(MostrarPerfil.getCognom());
        user.setText(MostrarPerfil.getUsername());
        cp.setText(MostrarPerfil.getCP());

        fotoperfil = findViewById(R.id.imagen_perfil);


        //CARREGAR IMATGE FIREBASE
        if (urlBD.substring(1, 7).equals("images")) {
            retrieveImage(urlBD);
        } else { //CARREGAR IMATGE DE GOOGLE
            fotoperfil.loadImageUrl(urlBD);
        }


    }

    @SuppressLint("IntentReset")
    private void obrirgaleria() {
        @SuppressLint("IntentReset") Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(Intent.createChooser(gallery, "Seleccione la Aplicación"), 10);
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
        //TODO: necessito recuperar l objecte desde el json. a child posarhi l indetificador guardat
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);

        //foto_post = (ImageView) findViewById(R.id.foto_postPerd);
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

}