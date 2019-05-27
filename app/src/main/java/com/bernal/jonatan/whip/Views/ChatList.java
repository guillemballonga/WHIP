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
import android.view.Menu;
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
    private ArrayList<ChatRelation> chatsUser;

    RecyclerView contenedor_chats;


    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");
    private String api = ul.getAPI_KEY();


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

        URL_chats = "https://whip-api.herokuapp.com/chats";

        Toolbar tool = findViewById(R.id.toolbar_listadoChats);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("CHATS");

        chatPresenter.getChats(URL_chats);
    }


    @Override
    public void chargeChats(final ArrayList user_chats) {
        //Aquí en realidad debería de llamar de nuevo al presenter, con los ids de user conseguir el username y la foto, y la que vuelva
        //de esa llamada sí hara esto de aquí, vaya percal xD
        userPresenter.getOthersInfo(user_chats);


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
                final String id_chat = chatsUser.get(contenedor_chats.getChildAdapterPosition(vista)).getId();
                AlertDialog.Builder alert = new AlertDialog.Builder(ChatList.this);
                alert.setMessage("¿Estás seguro que deseas eliminar este Comentario?")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chatPresenter.deleteChat(URL_chats, id_chat);

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("ELIMINAR CHAT");
                title.show();
            }

            @Override
            public void onChatClicked(int position, View vista) {
                /*ChatRelation chatRelation = (ChatRelation) userInfoForChat.get(contenedor_chats.getChildAdapterPosition(vista));
                String idChat = chatRelation.getId();
                Intent i = new Intent(ChatList.this, InfoEvent.class);
                i.putExtra("idChat", idChat);
                startActivity(i);
                //llamar a la activity de InfoChat*/
            }
        });
    }
}
