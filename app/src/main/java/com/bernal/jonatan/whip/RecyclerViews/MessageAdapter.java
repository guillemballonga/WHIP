package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.ChatMessage;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.InfoChat;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<ChatMessage> listaObjetos;
    private OnMessageListener onMessageListener;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    public MessageAdapter(List<ChatMessage> listaObjetos) {

        this.listaObjetos = listaObjetos;
    }

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.onMessageListener = onMessageListener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_message, viewGroup, false);

        return new MessageViewHolder(vista, listaObjetos, onMessageListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        if (listaObjetos.get(i).getUserId().equals(ul.getCorreo_user())) {
            messageViewHolder.time_right.setText(listaObjetos.get(i).getTime());
            messageViewHolder.content_right.setText(listaObjetos.get(i).getMessage());
            messageViewHolder.left.setVisibility(View.INVISIBLE);
        }

        else if (!listaObjetos.get(i).getUserId().equals(ul.getCorreo_user())){
            messageViewHolder.time_left.setText(listaObjetos.get(i).getTime());
            messageViewHolder.content_left.setText(listaObjetos.get(i).getMessage());
            messageViewHolder.right.setVisibility(View.INVISIBLE);
        }

        messageViewHolder.setId_chatMessage(listaObjetos.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
