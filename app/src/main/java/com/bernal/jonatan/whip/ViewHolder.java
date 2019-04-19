package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView imagen_postPerdi;
    TextView contenido_postPerdi, nombre_postPerdi;
    String id;

    public String getIdentificador() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ViewHolder(@NonNull View itemView, List<Fuente> datos) {
        super(itemView);

        imagen_postPerdi = (ImageView) itemView.findViewById(R.id.imagen_postPerd);
        contenido_postPerdi = (TextView) itemView.findViewById(R.id.Contenido_postPerd);
        nombre_postPerdi = (TextView) itemView.findViewById(R.id.Nombre_postPerd);

    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
    }
}
