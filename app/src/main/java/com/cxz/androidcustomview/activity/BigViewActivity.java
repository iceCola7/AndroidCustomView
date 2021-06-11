package com.cxz.androidcustomview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cxz.androidcustomview.R;
import com.cxz.bigviewlib.BigView;

import java.io.IOException;
import java.io.InputStream;

public class BigViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_view);

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