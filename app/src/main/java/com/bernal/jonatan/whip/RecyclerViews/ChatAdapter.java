package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<ChatRelation> listaObjetos;
    private OnChatListener onChatListener;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    public void setOnChatListener(OnChatListener onChatListener) {
        this.onChatListener = onChatListener;
    }

    public ChatAdapter(List<ChatRelation> listaObjects) {
        this.listaObjetos = listaObjects;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_chat, viewGroup, false);

        return new ChatViewHolder(vista, onChatListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        chatViewHolder.setId_chatRelation(listaObjetos.get(i).getId());
        if (ul.getCorreo_user().equals(listaObjetos.get(i).getUserIdOne()))
            chatViewHolder.user_chat.setText(listaObjetos.get(i).getUserIdTwo());
        else chatViewHolder.user_chat.setText(listaObjetos.get(i).getUserIdOne());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
