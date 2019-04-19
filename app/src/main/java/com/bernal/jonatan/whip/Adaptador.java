package com.bernal.jonatan.whip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{

    List<Fuente> listaObjetos;
    private View.OnClickListener listener;
    private String type;


    public Adaptador(List<Fuente> listaObjetos, String type) {

        this.listaObjetos = listaObjetos;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View vista = null;
        if (type.equals("Lost")) {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_lost, parent, false);
        }
        else if (type.equals("Adoption")) {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_adoption, parent, false);
        }
        else if (type.equals("User_Post")) {
            vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_userposts, parent, false);
        }

        vista.setOnClickListener(this);
        return new ViewHolder(vista, listaObjetos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.nombre_postPerdi.setText(listaObjetos.get(i).getNombre());


        if (!listaObjetos.get(i).getImagen().equals("")) retrieveImage(listaObjetos.get(i).getImagen(),holder);
        else holder.imagen_postPerdi.setImageResource(R.drawable.perro);

        holder.contenido_postPerdi.setText(listaObjetos.get(i).getContenido());

        if(type.equals("User_Post")) holder.type_post.setText(listaObjetos.get(i).getType());

        holder.setId(listaObjetos.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!= null) {
            listener.onClick(view);
        }

    }

    public void retrieveImage(String idImageFirebase, final ViewHolder view) {

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
                    view.imagen_postPerdi.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}
    }
}
