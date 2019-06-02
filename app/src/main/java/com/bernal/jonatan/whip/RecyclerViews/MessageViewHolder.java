package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernal.jonatan.whip.Models.ChatMessage;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.InfoChat;

import java.util.List;

public class MessageViewHolder extends RecyclerView.ViewHolder {


    ImageView delete_button;
    TextView user_message;
    TextView chat_message;
    TextView date_of_msg;
    TextView time_of_msg;

    public void setId_chatMessage(String id_chatMessage) {
        this.id_chatMessage = id_chatMessage;
    }

    private String id_chatMessage;

    public MessageViewHolder(@NonNull final View itemView, List<ChatMessage> listaObjetos, final OnMessageListener onMessageListener) {
        super(itemView);

        //text, date y time
        delete_button = itemView.findViewById(R.id.delete_chat);
        chat_message = itemView.findViewById(R.id.mensaje_chat);
        date_of_msg = itemView.findViewById(R.id.date_chat);
        time_of_msg = itemView.findViewById(R.id.time_chat);
        user_message = itemView.findViewById(R.id.user_chat);
        //text_area
        //date_of_msg
        //time_of_msg

        //Jhonny echame un cable con esto xD  if (!user_msg.equals(el user loggeado)) delete_button.setVisibility(View.GONE);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onMessageListener.onEliminateClicked(position, itemView);
                }
            }
        });
    }
}
