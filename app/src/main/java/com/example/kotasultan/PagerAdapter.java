package com.example.kotasultan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: return new Catatanku();
            case 1: return new Riwayat();
            case 2: return new ShakeToWin();
            default: return new Catatanku();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
