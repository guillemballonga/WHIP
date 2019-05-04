package com.bernal.jonatan.whip.RecyclerViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bernal.jonatan.whip.Models.Post;
import com.bernal.jonatan.whip.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Post> listaObjetos;
    private String type;
    private OnListListener onListListener;

    public void setOnListListener(OnListListener onListListener) {
        this.onListListener = onListListener;
    }

    public PostAdapter(List<Post> listaObjetos, String type) {

        this.listaObjetos = listaObjetos;
        this.type = type;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View vista = null;
        switch (type) {
            case "Lost":
                vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_lost, parent, false);
                break;
            case "Adoption":
                vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_adoption, parent, false);
                break;
            case "PostPropio":
                vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_userposts, parent, false);
                break;
        }

        assert vista != null;
        return new PostViewHolder(vista, listaObjetos,onListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int i) {

        holder.nombre_postPerdi.setText(listaObjetos.get(i).getNombre());


        if (!listaObjetos.get(i).getImagen().equals(""))
            retrieveImage(listaObjetos.get(i).getImagen(), holder);
        else holder.imagen_post.setImageResource(R.drawable.perro);

        holder.contenido_postPerdi.setText(listaObjetos.get(i).getContenido());

        if (type.equals("PostPropio")) holder.type_post.setText(listaObjetos.get(i).getType());

        holder.setId(listaObjetos.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }


    private void retrieveImage(String idImageFirebase, final PostViewHolder view) {

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
                    view.imagen_post.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException ignored) {
        }
    }


}
