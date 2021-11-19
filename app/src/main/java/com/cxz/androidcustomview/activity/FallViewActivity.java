package com.cxz.androidcustomview.activity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.fallviewlib.FallObject;
import com.cxz.fallviewlib.FallView;

public class FallViewActivity extends BaseActivity {

    private FallView fallView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fall_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        fallView = findViewById(R.id.fall_view);

        FallObject fallObject = new FallObject.Builder(getDrawable(R.mipmap.icon_snowflake_01))
                .build();
        FallObject fallObject2 = new FallObject.Builder(getDrawable(R.mipmap.icon_snowflake_02))
                .build();

        fallView.addFallObject(fallObject, 6);
        fallView.addFallObject(fallObject2, 6);
    }
}