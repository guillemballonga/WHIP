package com.bernal.jonatan.whip.RecyclerViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private List<ChatRelation> listaObjetos;
    private OnChatListener onChatListener;

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

        chatViewHolder.user_chat.setText(listaObjetos.get(i).getOtherUserName());

        if (listaObjetos.get(i).getPhotoUrl().equals("") || listaObjetos.get(i).getPhotoUrl() == null) {
            chatViewHolder.imagen_user.setImageResource(R.drawable.icono_usuario);
        } else if (listaObjetos.get(i).getPhotoUrl().substring(1, 7).equals("images")) {
            retrieveImage(listaObjetos.get(i).getPhotoUrl(), chatViewHolder);
        } else { //CARREGAR IMATGE DE GOOGLE
            chatViewHolder.imagen_user.loadImageUrl(listaObjetos.get(i).getPhotoUrl());
        }


        chatViewHolder.setId_chatRelation(listaObjetos.get(i).getId());


        chatViewHolder.setId_chatRelation(listaObjetos.get(i).getId());
    }


    private void retrieveImage(String idImageFirebase, final ChatViewHolder view) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //TODO: necessito recuperar l objecte desde el json. a child posarhi l indetificador guardat
        StorageReference storageReference = storage.getReferenceFromUrl("gs://whip-1553341713756.appspot.com/").child(idImageFirebase);

        //foto_post = (ImageView) findViewById(R.id.foto_postPerd);
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    view.imagen_user.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException ignored) {
        }
    }


    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }
}
