package com.bernal.jonatan.whip;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class OneFragmentMisPosts extends Fragment {
    View view;
    TextView txtTitle;
    String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_one_mis_posts, container, false);
        txtTitle=(TextView)view.findViewById(R.id.txtTitle);

        if (getArguments()!=null){
            title=getArguments().getString("title");
        }
        txtTitle.setText(title);

        return view;
    }
}
