package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.Event;
import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<Event> listaObjetos;
    private OnEventListener onEventListener;

    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    public EventAdapter(List<Event> listaObjetos) {

        this.listaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_events, viewGroup, false);
        return new EventViewHolder(vista,listaObjetos,onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        eventViewHolder.date_event.setText(listaObjetos.get(i).getDate());
        eventViewHolder.time_event.setText(listaObjetos.get(i).getTime());
        eventViewHolder.place_event.setText(listaObjetos.get(i).getPlace());
        eventViewHolder.user_event.setText(listaObjetos.get(i).getUserId());
        eventViewHolder.setId_event(listaObjetos.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
