package com.bernal.jonatan.whip.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bernal.jonatan.whip.MapsActivity;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class InfoPostAdoption extends AppCompatActivity implements ConcretePostPresenter.View {


    ConcretePostPresenter concretePostPresenter = new ConcretePostPresenter(this);
    TextView titulo, fecha, especie, raza, contenido;
    ImageView foto_post, compartirRRSS, maps;
    String Identificador;
    Button close_buton, solicitud_adopcion;
    String titlePost = "", descriptionPost = "";

    private String URL, URL_favs, URL_like, URL_close;


    TextView idCreador;

    private String mail_creador;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    private Menu menu_fav;
    private String idImage, coordenada1, coordenada2;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post_adoption);

        //Obtengo el ID del post
        Identificador = getIntent().getStringExtra("identificadorPost");

        titulo = findViewById(R.id.titulo_postAdoption);
        fecha = findViewById(R.id.fecha_postAdoption);
        especie = findViewById(R.id.especie_postAdoption);
        raza = findViewById(R.id.raza_postAdoption);
        contenido = findViewById(R.id.contenido_postAdoption);
        idCreador = findViewById(R.id.id_Creador_postAdoption);

        foto_post = findViewById(R.id.foto_postAdoption);
        compartirRRSS = findViewById(R.id.CompartirRRSSAdoption);
        solicitud_adopcion = findViewById(R.id.solicitud_adoption);

        maps = findViewById(R.id.abrir_GmapsAdoption);

        close_buton = findViewById(R.id.boton_cerrar_adoption);

        //Gestión toolbar
        Toolbar tool = findViewById(R.id.toolbar_infoPostAdoption);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(R.string.adopci_n);


        //Recoger los datos de Back y cargarlos en la vista
        URL = "https://whip-api.herokuapp.com/contributions/adoptionposts/" + Identificador;
        URL_favs = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=adoption";
        URL_like = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=adoption";
        URL_close = "https://whip-api.herokuapp.com/contributions/close/" + Identificador + "/?type=adoption";

        solicitud_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAdoptionRequest.setAdoptionPostID(Identificador);
                NewAdoptionRequest.setUsernameFromPost(mail_creador);
                startActivity(new Intent(InfoPostAdoption.this, NewAdoptionRequest.class));
                finish();
            }
        });


        compartirRRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoPostAdoption.this, ShareFacebook.class);
                i.putExtra("titlePost", titlePost);
                i.putExtra("descriptionPost", descriptionPost);
                i.putExtra("urlImage", idImage);
                startActivity(i);
            }
        });


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoPostAdoption.this, MapsActivity.class);
                i.putExtra("pos1", coordenada1);
                i.putExtra("pos2", coordenada2);
                startActivity(i);
            }
        });

        close_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tancar_post();
            }
        });

        concretePostPresenter.getPost(URL, "Adoption");
    }

    private void tancar_post() {
        AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostAdoption.this);
        alert.setMessage("¿Estás seguro que deseas cerrar este Post?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        concretePostPresenter.closePost(URL_close);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog title = alert.create();
        title.setTitle("CERRAR POST");
        title.show();
    }


    public boolean onCreateOptionsMenu(final Menu menu) {
        menu_fav = menu;
        concretePostPresenter.getFavorite(URL_favs);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_fav:
                BackFavs_like();
                break;
            case R.id.icono_fav_rell:

                BackFavs_dislike();

                break;

            case R.id.icono_delete:

                BackDelete();

                break;
        }
        return true;
    }

    private void BackDelete() {
        if (mail_creador.equals(ul.getCorreo_user())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostAdoption.this);
            alert.setMessage(R.string.delete_post)
                    .setCancelable(false)
                    .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            concretePostPresenter.deletePost(URL);
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog title = alert.create();
            title.setTitle(R.string.deletePost);
            title.show();

        } else
            Toast.makeText(getApplicationContext(), R.string.delete_post_creator, Toast.LENGTH_SHORT).show();
    }

    private void BackFavs_dislike() {
        Boolean like = false;
        concretePostPresenter.likePost(URL_like, like);
    }


    public void BackFavs_like() {
        Boolean like = true;
        concretePostPresenter.likePost(URL_like, like);
    }

    public void retrieveImage(String idImageFirebase) {

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
                    foto_post.setImageBitmap(bitmap);

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

        titulo.setText(title);
        fecha.setText(data[0]);
        especie.setText(specie);
        raza.setText(race);
        contenido.setText(text);
        mail_creador = userId;
        titlePost = title;
        descriptionPost = "WHIP - POST LOST : " + title + " DATE: " + data[0] + " IN: " + coord2 + "," + coord1;
        idCreador.setText(username);

        idImage = photo_url_1;
        coordenada1 = coord1;
        coordenada2 = coord2;
        //Fotografías con Firebase
        String urlFoto1 = photo_url_1; //LAURA->
        if (!urlFoto1.equals("")) retrieveImage(urlFoto1);
        else foto_post.setBackgroundResource(R.drawable.perfilperro);

        if (status) {
            close_buton.setVisibility(View.GONE);
            compartirRRSS.setVisibility(View.GONE);
            solicitud_adopcion.setVisibility(View.GONE);
        }

        if (mail_creador.equals(ul.getCorreo_user())) solicitud_adopcion.setVisibility(View.GONE);
        if (!mail_creador.equals(ul.getCorreo_user())) close_buton.setVisibility(View.GONE);


    }


    @Override
    public void setFavorite(Boolean fav) {
        if (fav) {
            getMenuInflater().inflate(R.menu.menu_infopostlikeuser, menu_fav);//si paso el menu de parametro se puede pero no sé si eso respeta el MVC
        } else {
            getMenuInflater().inflate(R.menu.menu_infopostuser, menu_fav);
        }
    }


    @Override
    public void setDeletePost() {
        finish();
    }

    @Override
    public void recharge() {
        recreate();
    }

    @Override
    public void notifyCreate(String id) {

    }
}
