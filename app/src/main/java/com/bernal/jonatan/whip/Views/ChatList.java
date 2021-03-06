package com.bernal.jonatan.whip.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bernal.jonatan.whip.Models.ChatRelation;
import com.bernal.jonatan.whip.Presenters.ChatPresenter;
import com.bernal.jonatan.whip.Presenters.UserPresenter;
import com.bernal.jonatan.whip.R;
import com.bernal.jonatan.whip.RecyclerViews.ChatAdapter;
import com.bernal.jonatan.whip.RecyclerViews.OnChatListener;

import java.util.ArrayList;

public class ChatList extends AppCompatActivity implements ChatPresenter.View, UserPresenter.View {


    ChatPresenter chatPresenter = new ChatPresenter(this);
    UserPresenter userPresenter = new UserPresenter(this);
    private String URL_chats;
    private ChatAdapter adapt;
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView contenedor_chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        contenedor_chats = findViewById(R.id.contenedor_chats);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_chats);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });

        URL_chats = "https://whip-api.herokuapp.com";

        Toolbar tool = findViewById(R.id.toolbar_listadoChats);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("CHATS");

        chatPresenter.getChats(URL_chats + "/chats");
    }

    @Override
    public void chargeChats(final ArrayList user_chats) {
        userPresenter.getOthersInfo(user_chats);
    }

    @Override
    public void recharge() {
        recreate();
    }

    @Override
    public void chargeMessages(ArrayList chat_messages) {

    }

    @Override
    public void notifyChatRelationCreate(String id) {

    }

    @Override
    public void getUserInfo(String cp, String email, String family_name, String first_name, String photoURL, String username) {

    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setUserPosts(ArrayList mis_posts) {

    }

    @Override
    public void sendInfoForChat(final ArrayList userInfoForChat) {
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        adapt = new ChatAdapter(userInfoForChat);
        contenedor_chats.setAdapter(adapt);
        contenedor_chats.setLayoutManager(layout);
        adapt.setOnChatListener(new OnChatListener() {
            @Override
            public void onEliminateClicked(int position, View vista) {
                final ChatRelation cr = (ChatRelation) userInfoForChat.get(contenedor_chats.getChildAdapterPosition(vista));
                final String id_chat = cr.getId();
                AlertDialog.Builder alert = new AlertDialog.Builder(ChatList.this);
                alert.setMessage(R.string.eliminar_chat)
                        .setCancelable(false)
                        .setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chatPresenter.deleteChat(URL_chats + "/chat", id_chat);

                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle(getString(R.string.eliminar_chat_titulo));
                title.show();
            }

            @Override
            public void onChatClicked(int position, View vista) {
                ChatRelation chatRelation = (ChatRelation) userInfoForChat.get(contenedor_chats.getChildAdapterPosition(vista));
                String idChat = chatRelation.getId();
                Intent i = new Intent(ChatList.this, InfoChat.class);
                i.putExtra("idChat", idChat);
                startActivity(i);
            }
        });
    }
}
