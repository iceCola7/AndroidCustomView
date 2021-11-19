package com.cxz.androidcustomview.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;

public class WaveView2Activity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_wave_view2;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);
    }
}
