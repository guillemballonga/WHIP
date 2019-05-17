package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;

public class InfoEvent extends AppCompatActivity implements ConcretePostPresenter.View{

    private String URL;
    private RequestQueue requestqueue;
    TextView titulo, fechaPost, fechaQuedada, hora, lugar, idSolicitante;
    private String idEvent;
    private Button acceptarQuedada, rebutjarQuedada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);

        idEvent = getIntent().getStringExtra("idEvent");


        acceptarQuedada = findViewById(R.id.boto_acceptar_event);
        rebutjarQuedada = findViewById(R.id.boton_cancelNewPostAdopcio);

        acceptarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                    Toast.makeText(getApplicationContext(), "Quedada acceptada", Toast.LENGTH_SHORT).show();

            }
        });

        rebutjarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Quedada rebutjada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InfoEvent.this, EventList.class));
                finish();
            }
        });

    }

    @Override
    public void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type) {

        titulo.setText(title);
        fechaPost.setText(data[0]);


    }



    @Override
    public void setFavorite(Boolean fav) {

    }

    @Override
    public void setDeletePost() {

    }

    @Override
    public void recharge() {

    }

    @Override
    public void notifyCreate(String id) {

    }



}
