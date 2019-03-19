package com.bernal.jonatan.whip;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<ViewHolder> {

    List<Fuente> listaObjetos;

    public Adaptador(List<Fuente> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(vista, listaObjetos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.nombre_postPerdi.setText(listaObjetos.get(i).getNombre());
        holder.imagen_postPerdi.setImageResource(listaObjetos.get(i).getImagen());
        holder.contenido_postPerdi.setText(listaObjetos.get(i).getContenido());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
