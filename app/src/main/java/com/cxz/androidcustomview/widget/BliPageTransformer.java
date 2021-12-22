package com.cxz.androidcustomview.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class BliPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        BliConstraintLayout bliConstraintLayout = (BliConstraintLayout) page;
        // 倾斜度
        int tiltDegree = 34;
        float v = position * tiltDegree;
        bliConstraintLayout.setRotateY(v);
        if (position > 0) {
            bliConstraintLayout.setIsLeftRotate(true);
        } else {
            bliConstraintLayout.setIsLeftRotate(false);
        }
    }

}
