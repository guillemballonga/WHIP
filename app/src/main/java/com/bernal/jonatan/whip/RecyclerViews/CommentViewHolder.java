package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    ImageView imagen_comment,delete_button;
    TextView contenido_comment,user_comment,data_comment;
    private String id_comment;
    Button comment_comment;
    EditText comment__comment_box;


    public CommentViewHolder(@NonNull final View itemView, final OnCommentListener onCommentListener) {
        super(itemView);

        imagen_comment = itemView.findViewById(R.id.imagen_comment);
        contenido_comment = itemView.findViewById(R.id.Contenido_comment);
        user_comment = itemView.findViewById(R.id.user_comment);
        data_comment = itemView.findViewById(R.id.date_comment);
        delete_button = itemView.findViewById(R.id.delete_comment);
        comment_comment = itemView.findViewById(R.id.crear_comment_comment);
        comment__comment_box = itemView.findViewById(R.id.response_comment);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onCommentListener.onEliminateClicked(position,itemView);
                }
            }
        });


        comment_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onCommentListener.onResponseComment(position,itemView);
                }
            }
        });



    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }
}
