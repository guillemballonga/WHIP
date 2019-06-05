package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.CalendarGoogle;
import com.bernal.jonatan.whip.Presenters.EventPresenter;
import com.bernal.jonatan.whip.R;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class InfoEvent extends AppCompatActivity implements EventPresenter.View {

    EventPresenter eventPresenter = new EventPresenter(this);

    private String URL, URLUpdateAccept, URLUpdateReject;
    private RequestQueue requestqueue;
    TextView title, dataPost, dataQuedada, hora, lloc, idSolicitante, id_destinatarioQuedada;
    String titleEvent = "", datePost = "", dateEvent = "", horaEvent = "", placeEvent = "", idSolicitanteEvent = "";
    private String idEvent;
    private Button acceptarQuedada, rebutjarQuedada, replanificarQuedada;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);


        idEvent = getIntent().getStringExtra("idEvent");


        //Toast.makeText(getApplicationContext(), "token usu " + ul.getToken(), Toast.LENGTH_SHORT).show();Post
        Toast.makeText(getApplicationContext(), "token usu " + ul.getToken(), Toast.LENGTH_SHORT).show();

        final String  authCode = ul.getToken();

        acceptarQuedada = findViewById(R.id.boto_acceptar_event);
        rebutjarQuedada = findViewById(R.id.boto_rebutjar_event);
        URL = "https://whip-api.herokuapp.com/event/" + idEvent;
        URLUpdateAccept = "https://whip-api.herokuapp.com/event/" + idEvent + "/answer?action=accept";
        URLUpdateReject = "https://whip-api.herokuapp.com/event/" + idEvent + "/answer?action=reject";

        title = findViewById(R.id.titulo_post_provinente);
        dataPost = findViewById(R.id.fecha_post_provinente);
        dataQuedada = findViewById(R.id.fecha_info_quedada);
        hora = findViewById(R.id.hora_info_quedada);
        lloc = findViewById(R.id.lugar_info_quedada);
        idSolicitante = findViewById(R.id.id_creadorQuedada);
        replanificarQuedada = findViewById(R.id.boto_replanificar_event);
        id_destinatarioQuedada = findViewById(R.id.id_destinatarioQuedada);



        acceptarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    eventPresenter.updateEvent(URLUpdateAccept);

                    //startActivity(new Intent(InfoEvent.this, EventList.class));
                    //finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //aqui crido a crear quedada al google calendar
                try {



                    int idCredentials = R.raw.clientextra;

                    InputStream im = credentials(idCredentials);


                    CalendarGoogle.apiCalendar(im, authCode);

                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (Exception e) {
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

        replanificarQuedada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    eventPresenter.updateEvent(URLUpdateReject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getApplicationContext(), "Introduce los datos para la nueva propuesta de quedada", Toast.LENGTH_SHORT).show();

                NewQuedada.setReplanificar();
                NewQuedada.setIdEvent(idEvent);
                NewQuedada.setUsernameFromPost(idSolicitanteEvent);
                startActivity(new Intent(InfoEvent.this, NewQuedada.class));
                finish();
            }
        });



        eventPresenter.getEventInfo(URL);



    }

    public InputStream credentials(int idCredentials) {
        Resources resources = getApplicationContext().getResources();
        InputStream is = resources.openRawResource(idCredentials);

        return is;
    }

    @Override
    public void setEvent(String userFromPostId, String userId, String place, String date, String time) {
        //title = findViewById(R.id.titulo_post_provinente);
        //dataPost = findViewById(R.id.fecha_post_provinente);
        dataQuedada.setText(date);
        hora.setText(time);
        lloc.setText(place);
        idSolicitante.setText(userId);
        id_destinatarioQuedada.setText(userFromPostId);


        placeEvent = place;
        horaEvent = time;

        dateEvent = date;
        idSolicitanteEvent = userId;

        if(idSolicitanteEvent.equals(ul.getCorreo_user())){

            acceptarQuedada.setVisibility(View.GONE);
            rebutjarQuedada.setVisibility(View.GONE);
            replanificarQuedada.setVisibility(View.GONE);
        }
    }

    @Override
    public void chargeEvents(ArrayList events) {

    }

    @Override
    public void recharge() {
        recreate();

    }

    @Override
    public void notifyNewQuedada() {

    }

    @Override
    public void notifyReplanificar() {

    }
}
