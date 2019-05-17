package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.R;

import java.util.List;

public class PostViewHolder extends RecyclerView.ViewHolder {

    ImageView imagen_post;
    TextView contenido_postPerdi, nombre_postPerdi, type_post;
    String id_post;

    PostViewHolder(@NonNull final View itemView, List<Post> datos, final OnListListener onListListener) {
        super(itemView);

        imagen_post = itemView.findViewById(R.id.imagen_postPerd);
        contenido_postPerdi = itemView.findViewById(R.id.Contenido_postPerd);
        nombre_postPerdi = itemView.findViewById(R.id.Nombre_postPerd);
        type_post = itemView.findViewById(R.id.tipoPost);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onListListener.onPostClicked(position,itemView);
                }
            }
        });

    }

    public String getIdentificador() {
        return id_post;
    }

    public void setId(String id) {
        this.id_post = id;
    }

}
