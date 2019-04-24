package com.bernal.jonatan.whip;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewPostAdoption extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    static ImageView foto;
    Spinner especie;
    Button create, cancel;
    EditText titulo, cp, raza, contenido;

    //variables para comucicación back
    private String URL;
    private RequestQueue requestqueue;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nou_post_adopcio);

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/adoptionposts/new";
        requestqueue = Volley.newRequestQueue(this);

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
                //openGallery();

                //Quan cliqui obrir UploadImagesFirebase -> per penjar la foto
                //startActivity(new Intent(NewPostAdoption.this, UploadImageFirebase.class));
                Intent i = new Intent(NewPostAdoption.this, UploadImageFirebase.class);
                //i.putExtra("idImageView", R.id.perfil_perroPerd);
                //i.putExtra("idImageView");
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

                //jason para comunicación con back
                JSONObject post = new JSONObject();
                JSONArray k = new JSONArray();

                //falta afegir imatge FIREBASE
                String identificadorImatge = UploadImageFirebase.getIdentificadorImatge();
                UploadImageFirebase.netejaIdentificadorImatge();
                k.put(identificadorImatge);
                k.put("");
                k.put("");
                k.put("");
//JASON
                try {
                    post.put("specie", especie.getSelectedItem().toString());
                    post.put("urls", k);
                    post.put("race", raza.getText().toString());
                    post.put("post_code", cp.getText().toString());
                    post.put("text", contenido.getText().toString());
                    post.put("title", titulo.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (titulo.getText().toString().equals("") || cp.getText().toString().equals("") || raza.getText().toString().equals("") || especie.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Los campos con * son obligatorios", Toast.LENGTH_SHORT).show();

                } else {
                    //Guardar los datos del formulario en BACK. NOTA: No olvidar guardar la fecha de creación del Post
                    JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                            JsonRequest.Method.POST,
                            URL,
                            post,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(), "Post guardado correctamente", Toast.LENGTH_SHORT).show();
                                    try {
                                        Intent i = new Intent(NewPostAdoption.this, InfoPostAdoption.class);
                                        i.putExtra("identificadorPost", response.getString("id"));
                                        startActivity(i);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "ERROOOOOOOR", Toast.LENGTH_SHORT).show();

                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", ul.getAPI_KEY()); //valor de V ha de ser el de la var global
                            return params;
                        }
                    };
                    requestqueue.add(objectJsonrequest);
//JASON

                    //Ir a ver el post en concreto


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

    @SuppressLint("IntentReset")
    public void openGallery() {
        @SuppressLint("IntentReset") Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/");
        startActivityForResult(gallery.createChooser(gallery, "Seleccione la Aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            Uri path = data.getData();
            foto.setImageURI(path);


            //Guardar el path de la foto en FIREBASE


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

}
