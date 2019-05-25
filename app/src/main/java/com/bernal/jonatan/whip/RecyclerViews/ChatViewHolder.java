package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    ImageView imagen_user, delete_button;
    TextView user_chat;
    private String id_chatRelation;


    public ChatViewHolder(@NonNull final View itemView, final OnChatListener onChatListener) {
        super(itemView);

        imagen_user = itemView.findViewById(R.id.imagen_user);
        delete_button = itemView.findViewById(R.id.delete_comment);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onChatListener.onEliminateClicked(position, itemView);
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onChatListener.onChatClicked(position, itemView);
                }
            }
        });

    }

    public String getId_chatRelation() {
        return id_chatRelation;
    }

    public void setId_chatRelation(String id_chatRelation) {
        this.id_chatRelation = id_chatRelation;
    }
}



