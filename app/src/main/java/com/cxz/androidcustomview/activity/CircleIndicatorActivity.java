package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.adapter.MyPagerAdapter;
import com.cxz.circleindicator.CircleIndicator;

public class CircleIndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_indicator);

        // DEFAULT
        ViewPager defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        MyPagerAdapter defaultPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        defaultViewpager.setAdapter(defaultPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);

        // CUSTOM
        ViewPager customViewpager = (ViewPager) findViewById(R.id.viewpager_custom);
        CircleIndicator customIndicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        MyPagerAdapter customPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        customViewpager.setAdapter(customPagerAdapter);
        customIndicator.setViewPager(customViewpager);
        customViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("OnPageChangeListener", "Current selected = " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // UNSELECTED BACKGROUND
        ViewPager unselectedBackgroundViewPager =
                (ViewPager) findViewById(R.id.viewpager_unselected_background);
        CircleIndicator unselectedBackgroundIndicator =
                (CircleIndicator) findViewById(R.id.indicator_unselected_background);
        MyPagerAdapter unselectedBackgroundPagerAdapter =
                new MyPagerAdapter(getSupportFragmentManager());
        unselectedBackgroundViewPager.setAdapter(unselectedBackgroundPagerAdapter);
        unselectedBackgroundIndicator.setViewPager(unselectedBackgroundViewPager);

    }
}
