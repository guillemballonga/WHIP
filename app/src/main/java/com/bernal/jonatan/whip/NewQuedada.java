package com.bernal.jonatan.whip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewQuedada extends AppCompatActivity {

    private String URL;
    static String postID, type;
    private int dia, mes, año, hora, min;
    private RequestQueue requestqueue;
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "");
    private String api = ul.getAPI_KEY();
    Button selecionar_fecha, seleccionar_hora, crear_quedada;
    EditText lugar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quedada);
        selecionar_fecha = findViewById(R.id.fecha_quedada);
        seleccionar_hora = findViewById(R.id.hora_quedada);
        lugar = findViewById(R.id.lugar_quedada);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_editarPerfil);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("QUEDADA");

        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/contributions/" + postID + "/event?type=" + type;
        requestqueue = Volley.newRequestQueue(this);


    }

    public void onClick(View view) {
        if (view == selecionar_fecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selecionar_fecha.setHint(dayOfMonth + "/" + month + "/" + year);
                }
            }
                    , dia, mes, año);
            datePickerDialog.show();
        }
        if (view == seleccionar_hora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR);
            min = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    seleccionar_hora.setHint(hourOfDay + ":" + minute);
                }
            }, hora, min, false);
            timePickerDialog.show();
        }
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
                quedada.put("lugar", lugar.getText().toString());

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
    }

    public static void setPostID(String idPost, String tipo) {
        postID = idPost;
        type = tipo;
    }


}