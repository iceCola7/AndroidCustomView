package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.shadowimage.ShadowImageView;

public class ShadowImageViewActivity extends BaseActivity {

    private SeekBar seekBar;
    private ShadowImageView shadowImageView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_shadow_image_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        seekBar = findViewById(R.id.seekbar);
        shadowImageView = findViewById(R.id.iv_shadow);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowImageView.setImageRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}