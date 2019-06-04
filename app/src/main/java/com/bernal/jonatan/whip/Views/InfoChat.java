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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    RecyclerView contenedor_mensajes;
    private ArrayList<ChatMessage> chat_msgs;
    String idChat;
    Button send_message;
    EditText messageToSend;

    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_chat);

        contenedor_mensajes = findViewById(R.id.contenedor_messages);
        send_message = findViewById(R.id.enviar_mensaje);
        messageToSend = findViewById(R.id.box_message);


        idChat = getIntent().getStringExtra("idChat");

        URL = "https://whip-api.herokuapp.com/chat/" + idChat;


        messageToSend.setText("");

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_message();
            }
        });


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
        messageToSend.setText("");
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
            public void onItemClicked(int position, View vista) {
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


    private void send_message() {

        if (messageToSend.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Introduzca su mensaje", Toast.LENGTH_SHORT).show();
        else {
            chatPresenter.createMessage(messageToSend.getText().toString(), URL);
        }

    }


}
