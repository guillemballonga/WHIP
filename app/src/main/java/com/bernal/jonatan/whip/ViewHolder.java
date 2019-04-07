package com.bernal.jonatan.whip;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.Post;

import java.util.List;

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


    public ViewHolder(@NonNull View itemView, List<Post> datos) {
        super(itemView);

        imagen_postPerdi = (ImageView) itemView.findViewById(R.id.imagen_postPerd);
        contenido_postPerdi = (TextView) itemView.findViewById(R.id.Contenido_postPerd);
        nombre_postPerdi = (TextView) itemView.findViewById(R.id.Nombre_postPerd);

    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        //aquí puedo usar los datos del post para lo que se necesite
    }
}
