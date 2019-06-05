package com.bernal.jonatan.whip.RecyclerViews;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.ChatMessage;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.InfoChat;

import java.util.List;

public class MessageViewHolder extends RecyclerView.ViewHolder {


    LinearLayout left, right;
    TextView content_left;
    TextView time_left;
    TextView content_right;
    TextView time_right;
    private String id_chatMessage;

    public void setId_chatMessage(String id_chatMessage) {
        this.id_chatMessage = id_chatMessage;
    }


    public MessageViewHolder(@NonNull final View itemView, final List<ChatMessage> listaObjetos, final OnMessageListener onMessageListener) {
        super(itemView);

        left = itemView.findViewById(R.id.chat_left);
        right = itemView.findViewById(R.id.chat_right);
        content_left = itemView.findViewById(R.id.mensaje_message_left);
        content_right = itemView.findViewById(R.id.mensaje_message_right);
        time_left = itemView.findViewById(R.id.date_message_left);
        time_right = itemView.findViewById(R.id.date_message_right);

        right.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onMessageListener.onItemClicked(position, itemView);
                }

                return true;
            }

        });

    }
}
