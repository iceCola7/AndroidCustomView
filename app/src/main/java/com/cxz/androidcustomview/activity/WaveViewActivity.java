package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.WaveBezierView;

public class WaveViewActivity extends AppCompatActivity {

    private WaveBezierView wave_bezier_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);

        wave_bezier_view = findViewById(R.id.wave_bezier_view);

        wave_bezier_view.startWave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        wave_bezier_view.stopWave();
    }
}
