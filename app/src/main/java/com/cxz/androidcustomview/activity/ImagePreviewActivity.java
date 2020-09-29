package com.cxz.androidcustomview.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.ZoomImageView;

public class ImagePreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZoomImageView imageView = new ZoomImageView(this);
        setContentView(imageView);

        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_temp));

    }
}
