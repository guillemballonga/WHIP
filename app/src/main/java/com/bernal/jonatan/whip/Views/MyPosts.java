package com.bernal.jonatan.whip.Views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bernal.jonatan.whip.R;

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
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.mis_posts);

        ViewPager viewPager = findViewById(R.id.viewpager);
        carregaViewPager(viewPager);
        tabLayout = findViewById(R.id.tabsMisPosts);
        tabLayout.setupWithViewPager(viewPager);
        setTitolsTabs();
    }

    private void setTitolsTabs() {
        tabLayout.getTabAt(0).setText(R.string.my_posts);
        tabLayout.getTabAt(1).setText(R.string.commented_posts);
        tabLayout.getTabAt(2).setText(R.string.favourite_posts);
    }


    private void carregaViewPager(ViewPager viewPager) {
        ViewPagerAdaptador adaptador = new ViewPagerAdaptador(getSupportFragmentManager());
        adaptador.addFragment(newInstance(getString(R.string.my_posts)));
        adaptador.addFragment(newInstance(getString(R.string.commented_posts)));
        adaptador.addFragment(newInstance(getString(R.string.favourite_posts)));
        viewPager.setAdapter(adaptador);
    }

    private OneFragmentMisPosts newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        OneFragmentMisPosts fragmentMisPosts = new OneFragmentMisPosts();
        fragmentMisPosts.setArguments(bundle);


        return fragmentMisPosts;

    }

}