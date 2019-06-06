package com.bernal.jonatan.whip.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.Comment;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.Views.UserLoggedIn;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Comment> listaObjetos;
    private OnCommentListener onCommentListener;
    private String type;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    public void setOnCommentListener(OnCommentListener onCommentListener) {
        this.onCommentListener = onCommentListener;
    }

    public CommentAdapter(List<Comment> listaObjects, String type) {
        this.listaObjetos = listaObjects;
        this.type = type;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_comment, viewGroup, false);

        return new CommentViewHolder(vista, onCommentListener, type);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.data_comment.setText(listaObjetos.get(i).getFecha());
        commentViewHolder.user_comment.setText(listaObjetos.get(i).getUser());
        commentViewHolder.contenido_comment.setText(listaObjetos.get(i).getContenido());
        commentViewHolder.setId_comment(listaObjetos.get(i).getId());

        if(!listaObjetos.get(i).getUser().equals(ul.getCorreo_user())) {
            commentViewHolder.delete_button.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
