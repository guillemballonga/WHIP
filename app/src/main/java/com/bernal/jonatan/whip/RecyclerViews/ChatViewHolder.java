package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.R;
import com.master.glideimageview.GlideImageView;

import java.util.List;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    ImageView delete_button;
    TextView user_chat;
    private String id_chatRelation;
    GlideImageView imagen_user;


    public ChatViewHolder(@NonNull final View itemView, final OnChatListener onChatListener) {
        super(itemView);

        imagen_user = itemView.findViewById(R.id.imagen_user);
        user_chat = itemView.findViewById(R.id.user_chat);
        delete_button = itemView.findViewById(R.id.delete_chat);


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

    public void setId_chatRelation(String id_chatRelation) {
        this.id_chatRelation = id_chatRelation;
    }
}



