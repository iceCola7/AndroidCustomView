package com.cxz.androidcustomview.activity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.bigviewlib.BigView;

import java.io.IOException;
import java.io.InputStream;

public class BigViewActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_big_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        BigView bigView = findViewById(R.id.bigView);
        InputStream is = null;
        try {
            is = getResources().getAssets().open("big.jpg");
            bigView.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}