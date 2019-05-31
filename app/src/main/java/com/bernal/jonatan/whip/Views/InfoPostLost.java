package com.bernal.jonatan.whip.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.Models.Comment;
import com.bernal.jonatan.whip.Presenters.CommentPresenter;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.CommentAdapter;
import com.bernal.jonatan.whip.RecyclerViews.OnCommentListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InfoPostLost extends AppCompatActivity implements ConcretePostPresenter.View, CommentPresenter.View {

    ConcretePostPresenter concretePostPresenter = new ConcretePostPresenter(this);
    CommentPresenter commentPresenter = new CommentPresenter(this);
    //private static final String  = ;
    TextView titulo, fecha, especie, tipo, raza, contenido, num_comments, idCreador;
    ImageView foto_post, foto_user, compartirRRSS;
    EditText box_comment;
    String Identificador;
    Button cerrar_post, crear_comment, borrar_comment, organ_quedada;
    RecyclerView comments;
    private RequestQueue requestqueue;


    private String URL, URL_favs, URL_like, URL_close, URL_comments;
    private CommentAdapter adapt;
    private ArrayList<Comment> Comments_post;

    private String mail_creador;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    private Menu menu_fav;
    private String titlePost, descriptionPost;
    private String idImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post_lost);

        //Obtengo el ID del post
        Identificador = getIntent().getStringExtra("identificadorPost");
        final String correuUsuari = ul.getCorreo_user();

        titulo = findViewById(R.id.titulo_postPerd);
        fecha = findViewById(R.id.fecha_postPerd);
        especie = findViewById(R.id.especie_postPerd);
        tipo = findViewById(R.id.tipo_postPerd);
        raza = findViewById(R.id.raza_postPerd);
        contenido = findViewById(R.id.contenido_postPerd);
        num_comments = findViewById(R.id.permitir_comentario);
        organ_quedada = findViewById(R.id.organ_quedadaPerd);
        idCreador = findViewById(R.id.id_Creador_postLost);

        foto_post = findViewById(R.id.foto_postPerd);
        foto_user = findViewById(R.id.imagen_coment_user);
        compartirRRSS = findViewById(R.id.CompartirRRSSPerd);
        //  organ_quedada = findViewById(R.id.organ_quedadaPerd);
        box_comment = findViewById(R.id.box_comment);


        cerrar_post = findViewById(R.id.boton_cerrar);
        crear_comment = findViewById(R.id.crear_comment);
        borrar_comment = findViewById(R.id.borrar_comment);


        comments = findViewById(R.id.contenedor_comments);

        //Gestión toolbar
        Toolbar tool = findViewById(R.id.toolbar_infoPostPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("LOST");

        box_comment.setText("");

        //Recoger los datos de Back y cargarlos en la vista
        URL = "https://whip-api.herokuapp.com/contributions/lostposts/" + Identificador;
        URL_favs = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=lost";
        URL_like = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=lost";
        URL_close = "https://whip-api.herokuapp.com/contributions/close/" + Identificador + "/?type=lost";
        URL_comments = "https://whip-api.herokuapp.com/contributions/lostposts/" + Identificador + "/comments";

        requestqueue = Volley.newRequestQueue(this);

        compartirRRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(InfoPostLost.this, ShareFacebook.class);
                i.putExtra("titlePost", titlePost);
                i.putExtra("descriptionPost", descriptionPost);
                i.putExtra("urlImage", idImage);
                startActivity(i);

            }
        });


        cerrar_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tancar_post();
            }
        });

        crear_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear_comment();
            }
        });

        borrar_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                box_comment.setText("");
            }
        });

        organ_quedada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mail_creador.equals(correuUsuari)){
                    Toast.makeText(getApplicationContext(), "No puedes crear una quedada contigo mismo", Toast.LENGTH_SHORT).show();
                }
                else {
                    NewQuedada.setPostID(Identificador, "lost");
                    NewQuedada.setUsernameFromPost(mail_creador);
                    startActivity(new Intent(InfoPostLost.this, NewQuedada.class));
                }
            }
        });



        concretePostPresenter.getPost(URL, "Lost");

        carregar_comments();

    }

    private void crear_comment() {

        if (box_comment.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe escribir un comentario", Toast.LENGTH_SHORT).show();
        else {
            commentPresenter.createComment(URL_comments, box_comment.getText().toString(),"");
        }

    }

    private void carregar_comments() {
        commentPresenter.getComments(URL_comments);
    }

    private void eliminar_comentari(View vista) {
        final String id_comment = Comments_post.get(comments.getChildAdapterPosition(vista)).getId();
        final String user_comment = Comments_post.get(comments.getChildAdapterPosition(vista)).getUser();
        if (user_comment.equals(ul.getCorreo_user())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
            alert.setMessage("¿Estás seguro que deseas eliminar este Comentario?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            commentPresenter.deleteComment(URL_comments, id_comment);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog title = alert.create();
            title.setTitle("ELIMINAR COMENTARIO");
            title.show();

        } else
            Toast.makeText(getApplicationContext(), "COMENTARIO NO CREADO POR TI, NO PUEDES BORRARLO", Toast.LENGTH_SHORT).show();
    }

    private void tancar_post() {
        //Toast.makeText(getApplicationContext(), "Cierro el post", Toast.LENGTH_SHORT).show();

        if (mail_creador.equals(ul.getCorreo_user())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
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

        } else
            Toast.makeText(getApplicationContext(), "POST NO CREADO POR EL TI, NO PUEDES BORRARLO", Toast.LENGTH_SHORT).show();

    }


    public boolean onCreateOptionsMenu(final Menu menu) {
        menu_fav = menu;
        concretePostPresenter.getFavorite(URL_favs);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.icono_fav:
                //comunicacion con back + cambiar color de la estrella

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

            AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
            alert.setMessage("¿Estás seguro que deseas eliminar este Post?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            concretePostPresenter.deletePost(URL);

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog title = alert.create();
            title.setTitle("ELIMINAR POST");
            title.show();

        } else
            Toast.makeText(getApplicationContext(), "POST NO CREADO POR EL TI, NO PUEDES BORRARLO", Toast.LENGTH_SHORT).show();
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
    public void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type, String username) {
        titulo.setText(title);
        fecha.setText(data[0]);
        especie.setText(specie);
        raza.setText(race);
        contenido.setText(text);
        mail_creador = userId;
        idCreador.setText(username);
        titlePost = title;
        descriptionPost = "Lost POST in Whip app ";
        idImage = photo_url_1;


        if (type.equals("F")) {
            tipo.setText("Encontrado");
        } else tipo.setText("Pérdida");

        //Fotografías con Firebase
        String urlFoto1 = photo_url_1; //LAURA->
        if (!urlFoto1.equals("")) retrieveImage(urlFoto1);
        else foto_post.setBackgroundResource(R.drawable.perfilperro);

        if (status) {
            cerrar_post.setVisibility(View.GONE);
            compartirRRSS.setVisibility(View.GONE);
            organ_quedada.setVisibility(View.GONE);
            foto_user.setVisibility(View.GONE);
            box_comment.setVisibility(View.GONE);
            crear_comment.setVisibility(View.GONE);
            borrar_comment.setVisibility(View.GONE);
        }

    }

    @Override
    public void setFavorite(Boolean fav) {
        if (fav) {
            getMenuInflater().inflate(R.menu.menu_infopostlikeuser, menu_fav); //si paso el menu de parametro se puede pero no sé si eso respeta el MVC
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

    @Override
    public void chargeCommentList(final ArrayList comments_post) {
        Comments_post = comments_post;
        num_comments.setText("Comentarios " + comments_post.size());
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new CommentAdapter(Comments_post, "CommentPost");
        adapt.setOnCommentListener(new OnCommentListener() {
            @Override
            public void onEliminateClicked(int position, View vista) {
                eliminar_comentari(vista);
            }

            @Override
            public void onVerCommentsClicked(View vista) {
                Comment comment = (Comment) comments_post.get(comments.getChildAdapterPosition(vista));
                String id_comment = comment.getId();
                String foto_comment = comment.getImagen();
                String user_comment = comment.getUser();
                String content_comment = comment.getContenido();
                String fecha_comment = comment.getFecha();
                Intent i = new Intent(InfoPostLost.this, CommentComment.class);
                i.putExtra("identificadorComment", id_comment);
                i.putExtra("fotoComment", foto_comment);
                i.putExtra("userComment", user_comment);
                i.putExtra("contentComment", content_comment);
                i.putExtra("fechaComment", fecha_comment);
                i.putExtra("idPost",Identificador);
                startActivity(i);
            }
        });
        comments.setAdapter(adapt);
        comments.setLayoutManager(layout);
    }

}