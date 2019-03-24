package com.bernal.jonatan.whip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login_button, signin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = (Button) findViewById(R.id.login);
        signin_button = (Button) findViewById(R.id.signin);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //coment

            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ListadoPerdida.class));
                //finish();
            }
        });


    }
}
