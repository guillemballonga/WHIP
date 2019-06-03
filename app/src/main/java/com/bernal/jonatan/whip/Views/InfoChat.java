package com.bernal.jonatan.whip.Views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.Presenters.ChatPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.MessageAdapter;

import java.util.ArrayList;

public class InfoChat extends AppCompatActivity implements ChatPresenter.View {


    ChatPresenter chatPresenter = new ChatPresenter(this);

    private String URL;
    private RequestQueue requestqueue;
    TextView chat_msg;
    private String idMessage;
    private Button deleteMessage;

    private MessageAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contenedor_mensajes;
    String idChat;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_chat);

        contenedor_mensajes = findViewById(R.id.contenedor_messages);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_messages);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });


        idChat = getIntent().getStringExtra("idChat");

        URL = "https://whip-api.herokuapp.com/chat/" + idChat;

        Toolbar tool = findViewById(R.id.toolbar_listadoMessages);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("MESSAGES");


        chatPresenter.getMessages(URL);
    }

    @Override
    public void chargeChats(ArrayList user_chats) {

    }

    @Override
    public void recharge() {

    }

    @Override
    public void chargeMessages(ArrayList chat_messages) {
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new MessageAdapter(chat_messages);
        contenedor_mensajes.setAdapter(adapt);
        contenedor_mensajes.setLayoutManager(layout);
  /*       adapt.setOnEventListener(new OnEventListener() {
            @Override
            public void onEventClicked(int position, View vista) {
                Event event = (Event) events.get(contenedor_events.getChildAdapterPosition(vista));
                String idEvent = event.getId();
                Intent i = new Intent(EventList.this, InfoEvent.class);
                i.putExtra("idEvent", idEvent);
                startActivity(i);
                //llamar a la activity de InfoEvent
            }
        });*/
    }
}
