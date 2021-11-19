package com.cxz.androidcustomview.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cxz.androidcustomview.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected TextView mTitleTV;

    protected abstract int attachLayoutRes();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        initView();
    }

    protected void setToolbarTitle(String title) {
        setToolbarTitle(title, true);
    }

    protected void setToolbarTitle(String title, boolean showHomeAsUp) {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("mToolbar is null.");
        }
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
        mTitleTV = findViewById(R.id.title_tv);
        mTitleTV.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
