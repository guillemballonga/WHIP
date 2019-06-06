package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bernal.jonatan.whip.Models.Event;
import com.bernal.jonatan.whip.Presenters.EventPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.EventAdapter;
import com.bernal.jonatan.whip.RecyclerViews.OnEventListener;

import java.util.ArrayList;

public class EventList extends AppCompatActivity implements EventPresenter.View {

    EventPresenter eventPresenter = new EventPresenter(this);
    private String URL_eventos;
    private EventAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contenedor_events;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        contenedor_events = findViewById(R.id.contenedor_events);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_events);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        URL_eventos = "https://whip-api.herokuapp.com/events";

        Toolbar tool = findViewById(R.id.toolbar_listadoChats);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("EVENTOS");

        eventPresenter.getEvents(URL_eventos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icono_calendario:
                break;
        }
        return true;
    }

    @Override
    public void setEvent(String UserFromPostId, String UserId, String Place, String Date, String Time) {

    }

    @Override
    public void chargeEvents(final ArrayList events) {
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new EventAdapter(events);
        contenedor_events.setAdapter(adapt);
        contenedor_events.setLayoutManager(layout);
        adapt.setOnEventListener(new OnEventListener() {
            @Override
            public void onEventClicked(int position, View vista) {
                Event event = (Event) events.get(contenedor_events.getChildAdapterPosition(vista));
                String idEvent = event.getId();
                Intent i = new Intent(EventList.this, InfoEvent.class);
                i.putExtra("idEvent", idEvent);
                startActivity(i);
            }
        });
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
