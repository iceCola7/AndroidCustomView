package com.cxz.androidcustomview.activity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;

public class TiltTextViewActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tilt_text_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);
    }
}
