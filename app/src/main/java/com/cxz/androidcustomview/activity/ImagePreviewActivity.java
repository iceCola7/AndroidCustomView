package com.cxz.androidcustomview.activity;

import android.graphics.BitmapFactory;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.androidcustomview.widget.ZoomImageView;

public class ImagePreviewActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        ZoomImageView zoomIV = findViewById(R.id.zoom_iv);
        zoomIV.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_temp));
    }
}
