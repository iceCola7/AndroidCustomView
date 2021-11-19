package com.cxz.androidcustomview.activity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.androidcustomview.widget.WaveBezierView;

public class WaveViewActivity extends BaseActivity {

    private WaveBezierView wave_bezier_view;
    private WaveBezierView wave_bezier_view2;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_wave_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

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
