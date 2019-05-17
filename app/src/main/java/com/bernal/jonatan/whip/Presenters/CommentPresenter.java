package com.bernal.jonatan.whip.Presenters;

import com.bernal.jonatan.whip.Servers.CommentServer;
import com.bernal.jonatan.whip.Servers.CommentServer;

import java.util.ArrayList;

public class CommentPresenter {

    View view;

    CommentServer commentServer;

    public CommentPresenter(View view) {
        this.view = view;
        this.commentServer = new CommentServer();
    }

    public void createComment(String URL_comments, String boxtext) {
        commentServer.createComment(this, URL_comments, boxtext);
    }

    public void getComments(String URL_comments) {
        commentServer.getComments(this, URL_comments);
    }

    public void deleteComment(String URL_comments, String id_comment) {
        commentServer.deleteComment(this, URL_comments, id_comment);
    }


    public View getView() {
        return view;
    }

    public void recharge() {
        view.recharge();
    }

    public void chargeCommentList(ArrayList comments_post) {
        view.chargeCommentList(comments_post);
    }

    public interface View {
        void recharge();

        void chargeCommentList(ArrayList comments_post);
    }

}