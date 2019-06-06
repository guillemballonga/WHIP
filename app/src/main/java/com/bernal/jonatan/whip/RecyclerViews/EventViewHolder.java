package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.Event;
import com.bernal.jonatan.whip.R;

import java.util.List;

public class EventViewHolder extends RecyclerView.ViewHolder {

    TextView user_event, place_event, time_event, date_event, texto_plano;
    String id_event;


    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public EventViewHolder(@NonNull final View itemView, List<Event> datos, final OnEventListener onEventListener) {
        super(itemView);

        user_event = itemView.findViewById(R.id.user_event);
        place_event = itemView.findViewById(R.id.place_event);
        time_event = itemView.findViewById(R.id.time_event);
        date_event = itemView.findViewById(R.id.date_event);
        texto_plano = itemView.findViewById(R.id.texto_informativo);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onEventListener.onEventClicked(position, itemView);
                }
            }
        });
    }


}
