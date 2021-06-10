package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.paletteimage.PaletteImageView;

public class PaletteImageViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PaletteImageView paletteImageView;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private SeekBar seekBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_image_view);

        paletteImageView = findViewById(R.id.palette);
        seekBar1 = findViewById(R.id.seek1);
        seekBar2 = findViewById(R.id.seek2);
        seekBar3 = findViewById(R.id.seek3);
        seekBar4 = findViewById(R.id.seek4);


        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar4.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek1:
                paletteImageView.setPaletteRadius(progress);
                break;
            case R.id.seek2:
                paletteImageView.setPaletteShadowRadius(progress);
                break;
            case R.id.seek3:
                paletteImageView.setPaletteShadowOffset(progress, 0);
                break;
            case R.id.seek4:
                paletteImageView.setPaletteShadowOffset(0, progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}