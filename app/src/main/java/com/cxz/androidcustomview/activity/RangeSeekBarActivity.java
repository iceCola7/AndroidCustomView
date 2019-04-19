package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cxz.androidcustomview.R;
import com.cxz.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class RangeSeekBarActivity extends AppCompatActivity {

    private String TAG = "RangeSeekBarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_seek_bar);

        RangeSeekBar rangeSeekBar = findViewById(R.id.rangeSeekBar);

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(15);
        list.add(30);
        list.add(45);
        list.add(60);
        list.add(90);
        list.add(120);
        list.add(150);
        list.add(180);
        list.add(360);

        rangeSeekBar.setSectionList(list);
        rangeSeekBar.setOnRangeSeekBarListener(new RangeSeekBar.OnRangeSeekBarListener() {
            public void onRangeChange(int minValue, int maxValue) {
                Log.e(TAG, "onRangeChange: " + minValue + "," + maxValue);
            }
        });
    }

}
