package com.cxz.androidcustomview.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cxz.androidcustomview.fragment.ColorFragment;

import java.util.Random;

/**
 * @author chenxz
 * @date 2018/8/27
 * @desc
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount = 5;

    private Random random = new Random();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int i) {
        return ColorFragment.newInstance(0xff000000 | random.nextInt(0x00ffffff));
    }

    @Override public int getCount() {
        return pagerCount;
    }

}
