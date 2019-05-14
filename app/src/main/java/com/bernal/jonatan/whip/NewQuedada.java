package com.bernal.jonatan.whip;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewQuedada extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private String URL;
    static String postID, type;
    private String dia, mes;
    private int hora, min, año;
    String horaForm="";
    String minForm="";
    private RequestQueue requestqueue;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    Button selecionar_fecha, crear_quedada, seleccionar_hora;
    EditText lugar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quedada);
        selecionar_fecha = findViewById(R.id.fecha_quedada);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_nova_quedada);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("QUEDADA");

        //JSON
        /*
        *  if (type.equals("adoption")) {
                        quedada.put("adoptionPostId", postID);
                    } else {
                        quedada.put("lostPostId", postID);
                    }
                    quedada.put("date", año + "-" + mes + "-" + dia + " " + hora + ":" + min + ": 00");
                    quedada.put("place", lugar.getText().toString());
        *
        * */


        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/" + postID + "/event?type=" + type;
        requestqueue = Volley.newRequestQueue(this);

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
                JSONObject quedada = new JSONObject();
                lugar = findViewById(R.id.lugar_quedada);
                seleccionar_hora = findViewById(R.id.hora_quedada);

                if (selecionar_fecha.getText().toString().equals("") || seleccionar_hora.getText().toString().equals("") || lugar.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }else {
                //JASON
                    try {
                        if (type.equals("adoption")){
                            quedada.put("adoptionPostId", postID);
                        } else {
                            quedada.put("lostPostId", postID);
                        }
                        quedada.put("date", año + "-" + mes + "-" + dia + " " + horaForm + ":" + minForm + ": 00");
                        quedada.put("place", lugar.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        //Guardar los datos del formulario en BACK.
                    JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                            JsonRequest.Method.PATCH,
                            URL,
                            quedada,

                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    startActivity(new Intent(NewQuedada.this, MainMenu.class));
                                    Toast.makeText(getApplicationContext(), "Quedada Creada Correctamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", api); //valor de V ha de ser el de la var global
                            return params;
                        }
                    };
                    requestqueue.add(objectJsonrequest);
//JASON
                }
            }
        });
    }

    private void selec_hora() {
        TimePickerDialog elijeHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay<10) {
                    horaForm = "0" + hourOfDay;
                }
                else if (hourOfDay>=10){
                    horaForm = "" + hourOfDay;
                }
                if(minute<10){
                    minForm = "0" + minute;
                }
                else if (minute>=10){
                    minForm = "" + minute;
                }
                seleccionar_hora.setText(horaForm+":"+minForm);
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

        month=month+1;

        if(month<10) {
            mes = "0" + month;
        }
        else if (month>10){
            mes = "" + month;
        }

        if(dayOfMonth<10){
            dia = "0" + dayOfMonth;
        }
        else if (dayOfMonth>10){
            dia = "" + dayOfMonth;
        }
        año=year;
        selecionar_fecha.setText(dia + "/" + mes + "/" + año);


    }


    /*public void onClick(View view) {
        if (view == crear_quedada) {

            //jason para comunicación con back
            JSONObject quedada = new JSONObject();


            //JASON
            try {
                if (type.equals("adoption")) {
                    quedada.put("adoptionPostId", postID);
                } else {
                    quedada.put("lostPostId", postID);
                }
                quedada.put("date", año + "-" + mes + "-" + dia + " " + hora + ":" + min + ": 00");
                quedada.put("place", lugar.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (selecionar_fecha.getHint().toString().equals("") || seleccionar_hora.getHint().toString().equals("") || lugar.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                //Guardar los datos del formulario en BACK. NOTA: No olvidar guardar la fecha de creación del Post
                JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                        JsonRequest.Method.PATCH,
                        URL,
                        quedada,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                startActivity(new Intent(NewQuedada.this, MainMenu.class));
                                Toast.makeText(getApplicationContext(), "Quedada Creada Correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/json");
                        params.put("Authorization", api); //valor de V ha de ser el de la var global
                        return params;
                    }
                };
                requestqueue.add(objectJsonrequest);
//JASON


            }
        }
    }*/

    public static void setPostID(String idPost, String tipo) {
        postID = idPost;
        type = tipo;
    }


}
