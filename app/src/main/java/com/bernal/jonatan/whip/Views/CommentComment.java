package com.bernal.jonatan.whip.Views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bernal.jonatan.whip.Models.Comment;
import com.bernal.jonatan.whip.Presenters.CommentPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.CommentAdapter;
import com.bernal.jonatan.whip.RecyclerViews.OnCommentListener;

import java.util.ArrayList;

public class CommentComment extends AppCompatActivity implements CommentPresenter.View {

    CommentPresenter commentPresenter = new CommentPresenter(this);
    ImageView foto_user;
    EditText box_comment_comment;
    String Identificador_comment, Identificador_post;
    Button crear_comment, borrar_comment;
    RecyclerView comments_comments;
    TextView date_comment, user_comment, content_comment;

    private String URL_create_comments, URL_list_comments;
    private CommentAdapter adapt;
    private ArrayList<Comment> Comments_comments;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_comment);

        Identificador_comment = getIntent().getStringExtra("identificadorComment");
        Identificador_post = getIntent().getStringExtra("idPost");

        date_comment = findViewById(R.id.date_comment_comment);
        user_comment = findViewById(R.id.user_comment_comment);
        content_comment = findViewById(R.id.Contenido_comment_comment);
        foto_user = findViewById(R.id.imagen_comment_comment);

        date_comment.setText(getIntent().getStringExtra("fechaComment"));
        user_comment.setText(getIntent().getStringExtra("userComment"));
        content_comment.setText(getIntent().getStringExtra("contentComment"));

        crear_comment = findViewById(R.id.crear_comment_comment);
        borrar_comment = findViewById(R.id.borrar_comment_comment);
        box_comment_comment = findViewById(R.id.box_comment_comment);
        comments_comments = findViewById(R.id.contenedor_comments_comments);

        URL_create_comments = "https://whip-api.herokuapp.com/contributions/lostposts/" + Identificador_post + "/comments";
        URL_list_comments = "https://whip-api.herokuapp.com/comments/" + Identificador_comment + "/children";


        crear_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear_comment();
            }
        });

        borrar_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                box_comment_comment.setText("");
            }
        });

        commentPresenter.getComments(URL_list_comments);

    }


    private void crear_comment() {

        if (box_comment_comment.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe escribir un comentario", Toast.LENGTH_SHORT).show();
        else {
            commentPresenter.createComment(URL_create_comments, box_comment_comment.getText().toString(), Identificador_comment);
        }
    }

    @Override
    public void recharge() {
        recreate();
    }

    @Override
    public void chargeCommentList(ArrayList comments_post) {
        Comments_comments = comments_post;
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new CommentAdapter(Comments_comments, "CommentComment");
        adapt.setOnCommentListener(new OnCommentListener() {
            @Override
            public void onEliminateClicked(int position, View vista) {
                eliminarComentari(vista);
                ;
            }

            @Override
            public void onVerCommentsClicked(View vista) {
                //Nothing to DO
            }
        });
        comments_comments.setAdapter(adapt);
        comments_comments.setLayoutManager(layout);

    }

    private void eliminarComentari(View view) {
        final String id_comment = Comments_comments.get(comments_comments.getChildAdapterPosition(view)).getId();
        final String user_comment = Comments_comments.get(comments_comments.getChildAdapterPosition(view)).getUser();
        if (user_comment.equals(ul.getCorreo_user())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CommentComment.this);
            alert.setMessage("¿Estás seguro que deseas eliminar este Comentario?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            commentPresenter.deleteComment(URL_create_comments, id_comment);
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
}
