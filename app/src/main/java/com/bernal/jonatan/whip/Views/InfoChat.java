package com.bernal.jonatan.whip.Views;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bernal.jonatan.whip.Models.ChatMessage;
import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.Presenters.ChatPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.MessageAdapter;
import com.bernal.jonatan.whip.RecyclerViews.OnMessageListener;

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
    private ArrayList<ChatMessage> chat_msgs;
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
        recreate();
    }

    @Override
    public void chargeMessages(ArrayList chat_messages) {
        chat_msgs = chat_messages;
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new MessageAdapter(chat_messages);
        contenedor_mensajes.setAdapter(adapt);
        contenedor_mensajes.setLayoutManager(layout);
        adapt.setOnMessageListener(new OnMessageListener() {
            @Override
            public void onEliminateClicked(int position, View vista) {
                //No habría que hacer la comparación del user aquí por q en teoría se haría en el adapter para ver si te saca la papelera o no
                final String id_msg = chat_msgs.get(contenedor_mensajes.getChildAdapterPosition(vista)).getId();
                AlertDialog.Builder alert = new AlertDialog.Builder(InfoChat.this);
                alert.setMessage("¿Estás seguro que deseas eliminar este mensaje?")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chatPresenter.deleteMessage(id_msg);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("ELIMINAR MENSAJE");
                title.show();
            }
        });
    }
}
