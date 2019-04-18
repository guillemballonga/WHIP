package com.bernal.jonatan.whip;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

public class MyPosts extends AppCompatActivity {

    private TabLayout tabLayout;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_posts);

        //Gestión de la toolbar
        Toolbar tool = findViewById(R.id.toolbar_MisPosts);
        setSupportActionBar(tool);
        Objects.requireNonNull(getSupportActionBar()).setTitle("MIS POSTS");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        carregaViewPager(viewPager);
        tabLayout = findViewById(R.id.tabsMisPosts);
        tabLayout.setupWithViewPager(viewPager);
        setTitolsTabs();
    }

    private void setTitolsTabs() {
        tabLayout.getTabAt(0).setText("Post Propios");
        tabLayout.getTabAt(1).setText("Post Comentados");
        tabLayout.getTabAt(2).setText("Post Favoritos");
    }


    private void carregaViewPager(ViewPager viewPager){
        ViewPagerAdaptador adaptador = new ViewPagerAdaptador(getSupportFragmentManager());
        adaptador.addFragment(newInstance("Post Propios text"));

        adaptador.addFragment(newInstance("Post Comentados text"));
        adaptador.addFragment(newInstance("Post Favoritos text "));
        viewPager.setAdapter(adaptador);
    }

    private OneFragmentMisPosts newInstance(String title){
        Bundle bundle=new Bundle();
        bundle.putString("title", title);
        OneFragmentMisPosts fragmentMisPosts=new OneFragmentMisPosts();
        fragmentMisPosts.setArguments(bundle);

        return fragmentMisPosts;

    }

}