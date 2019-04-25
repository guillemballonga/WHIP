package com.bernal.jonatan.whip;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imagen_postPerdi,imagen_comment;
    TextView contenido_postPerdi, nombre_postPerdi, type_post,contenido_comment,user_comment,data_comment;
    String id;
    String id_comment;




    ViewHolder(@NonNull View itemView, List<Fuente> datos) {
        super(itemView);

        imagen_postPerdi = itemView.findViewById(R.id.imagen_postPerd);
        contenido_postPerdi = itemView.findViewById(R.id.Contenido_postPerd);
        nombre_postPerdi = itemView.findViewById(R.id.Nombre_postPerd);
        type_post = itemView.findViewById(R.id.tipoPost);

        imagen_comment = itemView.findViewById(R.id.imagen_comment);
        contenido_comment = itemView.findViewById(R.id.Contenido_comment);
        user_comment = itemView.findViewById(R.id.user_comment);
        data_comment = itemView.findViewById(R.id.date_comment);

    }

    public String getIdentificador() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
    }
}
