package com.bernal.jonatan.whip.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Presenters.ConcretePostPresenter;
import com.bernal.jonatan.whip.R;

public class InfoEvent extends AppCompatActivity implements ConcretePostPresenter.View{

    private String URL;
    private RequestQueue requestqueue;
    TextView titulo, fechaPost, fechaQuedada, hora, lugar, idSolicitante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);




    }

    @Override
    public void getPostInfo(String title, String[] data, String specie, String race, String text, String userId, String photo_url_1, Boolean status, String type, String username) {

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
