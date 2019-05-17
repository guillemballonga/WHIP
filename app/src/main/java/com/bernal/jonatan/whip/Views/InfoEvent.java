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


        acceptarQuedada = findViewById(R.id.boto_acceptar_event);
        rebutjarQuedada = findViewById(R.id.boto_rebutjar_event);
        URLUpdateAccept = "/event/" + idEvent + "/answer?action=accept";
        URLUpdateReject = "/event/" + idEvent + "/answer?action=reject";

        acceptarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    eventPresenter.updateEvent(URLUpdateAccept);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(), "Quedada acceptada", Toast.LENGTH_SHORT).show();

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

    }



    @Override
    public void chargeEvents(ArrayList events) {

    }

    @Override
    public void recharge() {
        recreate();

    }
}
