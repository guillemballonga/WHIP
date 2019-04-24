package com.bernal.jonatan.whip;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imagen_postPerdi;
    TextView contenido_postPerdi, nombre_postPerdi, type_post;
    String id;

    ViewHolder(@NonNull View itemView, List<Fuente> datos) {
        super(itemView);

        imagen_postPerdi = itemView.findViewById(R.id.imagen_postPerd);
        contenido_postPerdi = itemView.findViewById(R.id.Contenido_postPerd);
        nombre_postPerdi = itemView.findViewById(R.id.Nombre_postPerd);
        type_post = itemView.findViewById(R.id.tipoPost);

    }

    public String getIdentificador() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
    }
}
