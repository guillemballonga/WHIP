package com.bernal.jonatan.whip.RecyclerViews;

import android.view.View;

public interface OnCommentListener {

    void onEliminateClicked(int position, View vista);
    void onResponseComment(int position, View vista);
}
