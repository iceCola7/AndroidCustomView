package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.shadowimage.ShadowImageView;

public class ShadowImageViewActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private ShadowImageView shadowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_image_view);

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