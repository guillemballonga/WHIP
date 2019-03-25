package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EditarPerfil extends AppCompatActivity {


    ImageButton goToMostrarPerfilGuardant;
    ImageButton goToMostrarPerfilCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        goToMostrarPerfilGuardant = (ImageButton) findViewById(R.id.boto_guardar);
        goToMostrarPerfilCancelar = (ImageButton) findViewById(R.id.boto_cancelar);

        goToMostrarPerfilGuardant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditarPerfil.this, MostrarPerfil.class));
                //finish();
                
                setNousParametres();
            }
            
            
            
        });

        goToMostrarPerfilCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditarPerfil.this, MostrarPerfil.class));
                //finish();
            }
        });



    }

    private void setNousParametres() {


    }


}
