package com.bernal.jonatan.whip.Views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.bernal.jonatan.whip.DatePickerFragment;
import com.bernal.jonatan.whip.Presenters.EventPresenter;
import com.bernal.jonatan.whip.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class NewQuedada extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, EventPresenter.View {


    EventPresenter eventPresenter = new EventPresenter(this);

    private String URL;
    static String postID, type, idEvento;
    static String UsernameFromPost;
    private String dia, mes;
    private int hora, min, año;
    String horaForm = "";
    String minForm = "";
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    Button selecionar_fecha, crear_quedada, seleccionar_hora;
    EditText lugar;
    static int replanificar = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quedada);
        selecionar_fecha = findViewById(R.id.fecha_quedada);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_nova_quedada);
        setSupportActionBar(tool);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.meeting);

        //Coneixón con la API
        if (replanificar == 0) {
            URL = "https://whip-api.herokuapp.com/contributions/" + postID + "/event?type=" + type;
        } else {
            URL = "https://whip-api.herokuapp.com/event/" + idEvento;
        }
        seleccionar_hora = findViewById(R.id.hora_quedada);
        seleccionar_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);
                selec_hora();
            }
        });

        selecionar_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment escollirData = new DatePickerFragment();
                escollirData.show(getSupportFragmentManager(), "escojer fecha");
            }
        });


        crear_quedada = findViewById(R.id.boton_enviar_quedada);
        crear_quedada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jason para comunicación con back

                lugar = findViewById(R.id.lugar_quedada);
                seleccionar_hora = findViewById(R.id.hora_quedada);
                if (selecionar_fecha.getText().toString().equals("") || seleccionar_hora.getText().toString().equals("") || lugar.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.campost_obligatorios), Toast.LENGTH_SHORT).show();
                } else {
                    if (replanificar == 0)
                        eventPresenter.newQuedada(postID, año, mes, dia, horaForm, minForm, lugar, UsernameFromPost, type, URL);
                    else {
                        replanificar = 0;
                        eventPresenter.replanificarQuedada(postID, año, mes, dia, horaForm, minForm, lugar, UsernameFromPost, URL);
                    }
                }
            }
        });
    }

    private void selec_hora() {
        TimePickerDialog elijeHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (hourOfDay < 10) {
                    horaForm = "0" + hourOfDay;
                } else {
                    horaForm = "" + hourOfDay;
                }
                if (minute < 10) {
                    minForm = "0" + minute;
                } else {
                    minForm = "" + minute;
                }
                seleccionar_hora.setText(horaForm + ":" + minForm);
            }
        }, hora, min, false);
        elijeHora.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        month = month + 1;

        if (month < 10) {
            mes = "0" + month;
        } else if (month > 10) {
            mes = "" + month;
        }

        if (dayOfMonth < 10) {
            dia = "0" + dayOfMonth;
        } else if (dayOfMonth > 10) {
            dia = "" + dayOfMonth;
        }
        año = year;
        selecionar_fecha.setText(dia + "/" + mes + "/" + año);


    }

    public static void setPostID(String idPost, String tipo) {
        postID = idPost;
        type = tipo;
    }

    public static void setIdEvent(String idEvent) {
        idEvento = idEvent;
    }

    public static void setUsernameFromPost(String username) {
        UsernameFromPost = username;
    }

    public static void setReplanificar() {
        replanificar = 1;
    }


    @Override
    public void setEvent(String UserFromPostId, String UserId, String Place, String Date, String Time) {

    }

    @Override
    public void chargeEvents(ArrayList events) {

    }

    @Override
    public void recharge() {

    }

    @Override
    public void notifyNewQuedada() {
        startActivity(new Intent(NewQuedada.this, MainMenu.class));
        finish();
    }

    @Override
    public void notifyReplanificar() {
        replanificar = 0;
        startActivity(new Intent(NewQuedada.this, MainMenu.class));
        finish();
    }
}
