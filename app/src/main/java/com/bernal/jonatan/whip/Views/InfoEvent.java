package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Presenters.EventPresenter;
import com.bernal.jonatan.whip.R;

import org.json.JSONException;

import java.util.ArrayList;


public class InfoEvent extends AppCompatActivity implements EventPresenter.View{

    EventPresenter eventPresenter = new EventPresenter( this);

    private String URL, URLUpdateAccept, URLUpdateReject ;
    private RequestQueue requestqueue;
    TextView titulo, fechaPost, fechaQuedada, hora, lugar, idSolicitante;
    private String idEvent;
    private Button acceptarQuedada, rebutjarQuedada;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);

        idEvent = getIntent().getStringExtra("idEvent");

<<<<<<< HEAD

        Toast.makeText(getApplicationContext(), "token usu " + ul.getToken(), Toast.LENGTH_SHORT).show();
        String xxx = ul.getToken();
        System.out.println("token id: " + ul.getToken());


=======
>>>>>>> CarregarInfoEvent
        acceptarQuedada = findViewById(R.id.boto_acceptar_event);
        rebutjarQuedada = findViewById(R.id.boto_rebutjar_event);
        URL = "https://whip-api.herokuapp.com/event/" + idEvent;
        URLUpdateAccept = "https://whip-api.herokuapp.com/event/" + idEvent + "/answer?action=accept";
        URLUpdateReject = "https://whip-api.herokuapp.com/event/" + idEvent + "/answer?action=reject";

        titulo = findViewById(R.id.titulo_post_provinente);
        fechaPost = findViewById(R.id.fecha_post_provinente);
        fechaQuedada = findViewById(R.id.fecha_info_quedada);
        hora = findViewById(R.id.hora_info_quedada);
        lugar = findViewById(R.id.lugar_info_quedada);
        idSolicitante = findViewById(R.id.id_creadorQuedada);



        acceptarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    eventPresenter.updateEvent(URLUpdateAccept);
                    startActivity(new Intent(InfoEvent.this, EventList.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(), "Quedada acceptada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InfoEvent.this, EventList.class));
                finish();

            }
        });

        rebutjarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    eventPresenter.updateEvent(URLUpdateReject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Toast.makeText(getApplicationContext(), "Quedada rebutjada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InfoEvent.this, EventList.class));
                finish();
            }
        });

        eventPresenter.getEventInfo(URL);

    }


    @Override
    public void setEvent(String UserFromPostId, String UserId, String Place, String Date, String Time) {
        //titulo = findViewById(R.id.titulo_post_provinente);
        //fechaPost = findViewById(R.id.fecha_post_provinente);
        fechaQuedada.setText(Date);
        hora.setText(Time);
        lugar.setText(Place);
        idSolicitante.setText(UserFromPostId);



    }

    @Override
    public void chargeEvents(ArrayList events) {

    }

    @Override
    public void recharge() {
        recreate();

    }
}
