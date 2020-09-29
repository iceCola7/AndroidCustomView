package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.LoadingLineView;
import com.cxz.androidcustomview.widget.LoadingView;

public class LoadingActivity extends AppCompatActivity {

    private LoadingView loadingView;
    private LoadingLineView loadingLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingView = findViewById(R.id.loading_view);
        loadingView.startAnimation();
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.startAnimation();
            }
        });
        loadingLineView = findViewById(R.id.loading_line_view);
        loadingLineView.startLoading();

    }
}
