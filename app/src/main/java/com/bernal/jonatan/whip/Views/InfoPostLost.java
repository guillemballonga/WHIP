package com.bernal.jonatan.whip.Views;

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

import com.bernal.jonatan.whip.MapsActivity;
import com.bernal.jonatan.whip.Models.Comment;
import com.bernal.jonatan.whip.Presenters.ChatPresenter;
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

public class InfoPostLost extends AppCompatActivity implements ConcretePostPresenter.View, CommentPresenter.View, ChatPresenter.View {

    ConcretePostPresenter concretePostPresenter = new ConcretePostPresenter(this);
    CommentPresenter commentPresenter = new CommentPresenter(this);
    ChatPresenter chatPresenter = new ChatPresenter(this);
    TextView titulo, fecha, especie, tipo, raza, contenido, num_comments, idCreador;
    ImageView foto_post, compartirRRSS, maps;
    EditText box_comment;
    String Identificador;
    Button cerrar_post, crear_comment, borrar_comment, organ_quedada, chat_privado;
    RecyclerView comments;


    private String URL, URL_favs, URL_like, URL_close, URL_comments, URL_chat;
    private CommentAdapter adapt;
    private ArrayList<Comment> Comments_post;

    private String mail_creador;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    private Menu menu_fav;
    private String titlePost, descriptionPost;
    private String idImage, coordenada1, coordenada2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_post_lost);

        //Obtengo el ID del post
        Identificador = getIntent().getStringExtra("identificadorPost");

        titulo = findViewById(R.id.titulo_postPerd);
        fecha = findViewById(R.id.fecha_postPerd);
        especie = findViewById(R.id.especie_postPerd);
        tipo = findViewById(R.id.tipo_postPerd);
        raza = findViewById(R.id.raza_postPerd);
        contenido = findViewById(R.id.contenido_postPerd);
        num_comments = findViewById(R.id.permitir_comentario);
        organ_quedada = findViewById(R.id.organ_quedadaPerd);
        idCreador = findViewById(R.id.id_Creador_postLost);

        maps = findViewById(R.id.abrir_GmapsPerd);

        foto_post = findViewById(R.id.foto_postPerd);
        compartirRRSS = findViewById(R.id.CompartirRRSSPerd);
        box_comment = findViewById(R.id.box_comment);


        cerrar_post = findViewById(R.id.boton_cerrar);
        crear_comment = findViewById(R.id.crear_comment);
        borrar_comment = findViewById(R.id.borrar_comment);
        chat_privado = findViewById(R.id.chat_privado);


        comments = findViewById(R.id.contenedor_comments);

        //Gestión toolbar
        Toolbar tool = findViewById(R.id.toolbar_infoPostPerd);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(R.string.p_rdida);

        box_comment.setText("");

        //Recoger los datos de Back y cargarlos en la vista
        URL = "https://whip-api.herokuapp.com/contributions/lostposts/" + Identificador;
        URL_favs = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=lost";
        URL_like = "https://whip-api.herokuapp.com/contributions/" + Identificador + "/like/?type=lost";
        URL_close = "https://whip-api.herokuapp.com/contributions/close/" + Identificador + "/?type=lost";
        URL_chat = "https://whip-api.herokuapp.com/chat";
        URL_comments = "https://whip-api.herokuapp.com/contributions/lostposts/" + Identificador + "/comments";


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
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InfoPostLost.this, MapsActivity.class);
                i.putExtra("pos1", coordenada1);
                i.putExtra("pos2", coordenada2);
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
                NewQuedada.setPostID(Identificador, "lost");
                NewQuedada.setUsernameFromPost(mail_creador);
                startActivity(new Intent(InfoPostLost.this, NewQuedada.class));
            }
        });

        chat_privado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatPresenter.createChat(URL_chat, mail_creador);
            }
        });


        concretePostPresenter.getPost(URL, "Lost");

        carregar_comments();

    }

    private void crear_comment() {

        if (box_comment.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), R.string.write_comment, Toast.LENGTH_SHORT).show();
        else {
            commentPresenter.createComment(URL_comments, box_comment.getText().toString(), "");
        }

    }

    private void carregar_comments() {
        commentPresenter.getComments(URL_comments);
    }

    private void eliminar_comentari(View vista) {
        final String id_comment = Comments_post.get(comments.getChildAdapterPosition(vista)).getId();
        AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
        alert.setMessage(R.string.delete_comment)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commentPresenter.deleteComment(URL_comments, id_comment);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog title = alert.create();
        title.setTitle(R.string.delete_comment_may);
        title.show();
    }

    private void tancar_post() {
        AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
        alert.setMessage(R.string.close_post)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        concretePostPresenter.closePost(URL_close);

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog title = alert.create();
        title.setTitle(R.string.cerrar_post);
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
            AlertDialog.Builder alert = new AlertDialog.Builder(InfoPostLost.this);
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
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);

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
        idCreador.setText(username);
        titlePost = title;
        descriptionPost = "WHIP - POST LOST : " + title + " DATE: " + data[0] + " IN: " + coord2 + "," + coord1;
        idImage = photo_url_1;
        coordenada1 = coord1;
        coordenada2 = coord2;


        if (type.equals("F")) {
            tipo.setText(getString(R.string.found));
        } else tipo.setText(getString(R.string.lost));

        //Fotografías con Firebase
        String urlFoto1 = photo_url_1; //LAURA->
        if (!urlFoto1.equals("")) retrieveImage(urlFoto1);
        else foto_post.setBackgroundResource(R.drawable.perfilperro);

        if (mail_creador.equals(ul.getCorreo_user())) {
            chat_privado.setVisibility(View.GONE);
            organ_quedada.setVisibility(View.GONE);
        }
        if (!mail_creador.equals(ul.getCorreo_user())) cerrar_post.setVisibility(View.GONE);


        if (status) {
            cerrar_post.setVisibility(View.GONE);
            compartirRRSS.setVisibility(View.GONE);
            organ_quedada.setVisibility(View.GONE);
            box_comment.setVisibility(View.GONE);
            crear_comment.setVisibility(View.GONE);
            borrar_comment.setVisibility(View.GONE);
            chat_privado.setVisibility(View.GONE);
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
    public void chargeChats(ArrayList user_chats) {

    }

    @Override
    public void recharge() {
        recreate();
    }

    @Override
    public void chargeMessages(ArrayList chat_messages) {

    }

    @Override
    public void notifyChatRelationCreate(String id) {
        Intent i = new Intent(InfoPostLost.this, InfoChat.class);
        i.putExtra("idChat", id);
        startActivity(i);
        finish();
    }

    @Override
    public void notifyCreate(String id) {

    }

    @Override
    public void chargeCommentList(final ArrayList comments_post) {
        Comments_post = comments_post;
        num_comments.setText(R.string.comments + comments_post.size());
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
                i.putExtra("idPost", Identificador);
                startActivity(i);
            }
        });
        comments.setAdapter(adapt);
        comments.setLayoutManager(layout);
    }

}