package com.bernal.jonatan.whip.Presenters;

import com.bernal.jonatan.whip.Servers.ChatServer;
import com.bernal.jonatan.whip.Views.ChatList;

import java.util.ArrayList;


public class ChatPresenter {

    View view;

    ChatServer chatServer;


    public ChatPresenter(View view) {
        this.view = view;
        this.chatServer = new ChatServer();
    }

    public void getChats(String url_chats) {
        chatServer.getChats(this, url_chats);
    }

    public void chargeChats(ArrayList user_chats) {
        view.chargeChats(user_chats);
    }

    public Object getView() {
        return this.view;
    }

    public void deleteChat(String url_chats, String id_chat) {
        chatServer.deleteChat(this, url_chats, id_chat);
    }

    public void recharge() {
        view.recharge();
    }

    public void getMessages(String url) {
        chatServer.getMessages(this, url);
    }

    public void chargeMessages(ArrayList chat_messages) {
        view.chargeMessages(chat_messages);
    }

    public void deleteMessage(String id_msg) {
        chatServer.deleteMessage(this, id_msg);
    }

    public void createMessage(String message, String URL) {
        chatServer.sendMessage(this, message, URL);
    }


    public interface View {

        void chargeChats(ArrayList user_chats);

        void recharge();

        void chargeMessages(ArrayList chat_messages);
    }


}
