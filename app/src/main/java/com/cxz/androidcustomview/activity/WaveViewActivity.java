package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.WaveBezierView;

public class WaveViewActivity extends AppCompatActivity {

    private WaveBezierView wave_bezier_view;
    private WaveBezierView wave_bezier_view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);

        wave_bezier_view = findViewById(R.id.wave_bezier_view);
        wave_bezier_view2 = findViewById(R.id.wave_bezier_view2);

        wave_bezier_view.startWave();
        wave_bezier_view2.startWave();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wave_bezier_view.resumeWave();
        wave_bezier_view2.resumeWave();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wave_bezier_view.pauseWave();
        wave_bezier_view2.pauseWave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        wave_bezier_view.stopWave();
        wave_bezier_view2.stopWave();
    }
}
