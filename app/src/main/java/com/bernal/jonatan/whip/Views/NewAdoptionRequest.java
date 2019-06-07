package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bernal.jonatan.whip.Presenters.AdoptionRequestPresenter;
import com.bernal.jonatan.whip.R;

import java.util.Objects;

public class NewAdoptionRequest extends AppCompatActivity implements AdoptionRequestPresenter.View {

    AdoptionRequestPresenter adoptionRequestPresenter = new AdoptionRequestPresenter(this);

    Button toQuedada;
    static String UsernameFromPost;
    static String AdoptionPostID;
    String photo_url = "";
    EditText cosText;
    private String URL;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_solicitud_adopcion);

        //Gestión de las Toolbars
        Toolbar tool = findViewById(R.id.toolbar_nova_solicitud_adopt);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.abandono_o_p_rdida);

        cosText = findViewById(R.id.descripcion_motivo_adopcion);
        toQuedada = findViewById(R.id.boton_enviar_quedada);
        toQuedada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envia_dades_back();
            }
        });

    }

    public static void setAdoptionPostID(String id) {
        AdoptionPostID = id;
    }

    public static void setUsernameFromPost(String username) {
        UsernameFromPost = username;
    }

    private void envia_dades_back() {

        URL = "https://whip-api.herokuapp.com/event/adoptionrequest";
        adoptionRequestPresenter.sendInfo(URL, cosText.getText().toString(), AdoptionPostID, photo_url, UsernameFromPost);
    }

    @Override
    public void notifyCreate() {
        NewQuedada.setPostID(AdoptionPostID, "adoption");
        NewQuedada.setUsernameFromPost(UsernameFromPost);
        startActivity(new Intent(NewAdoptionRequest.this, NewQuedada.class));
        finish();
    }

    @Override
    public void notifyEmptyDesc() {
        Toast.makeText(getApplicationContext(), "Introduce una descripción", Toast.LENGTH_SHORT).show();
    }
}
