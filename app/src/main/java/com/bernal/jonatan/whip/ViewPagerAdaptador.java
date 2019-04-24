package com.bernal.jonatan.whip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaptador extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    ViewPagerAdaptador(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}
