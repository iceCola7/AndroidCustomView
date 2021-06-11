package com.cxz.androidcustomview.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.fallviewlib.FallObject;
import com.cxz.fallviewlib.FallView;

public class FallViewActivity extends AppCompatActivity {

    private FallView fallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_view);

        fallView = findViewById(R.id.fall_view);

        FallObject fallObject = new FallObject.Builder(getDrawable(R.mipmap.icon_snowflake_01))
                .build();
        FallObject fallObject2 = new FallObject.Builder(getDrawable(R.mipmap.icon_snowflake_02))
                .build();

        fallView.addFallObject(fallObject, 6);
        fallView.addFallObject(fallObject2, 6);

    }
}