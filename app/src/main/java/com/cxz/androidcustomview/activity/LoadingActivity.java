package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.androidcustomview.widget.LoadingLineView;
import com.cxz.androidcustomview.widget.LoadingView;

public class LoadingActivity extends BaseActivity {

    private LoadingView loadingView;
    private LoadingLineView loadingLineView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

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
