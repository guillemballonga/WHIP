package com.bernal.jonatan.whip.RecyclerViews;

import android.view.View;

public interface OnChatListener {
    void onEliminateClicked(int position, View vista);

    void onChatClicked(int position, View itemView);
}
