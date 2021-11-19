package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.androidcustomview.util.XLog;
import com.cxz.bubbledragview.BubbleDragView;

public class BubbleDrawViewActivity extends BaseActivity {

    private static final String TAG = BubbleDrawViewActivity.class.getName();

    private BubbleDragView mBubbleDragView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_bubble_draw_view;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        mBubbleDragView = findViewById(R.id.bubbleDragView);
        mBubbleDragView.setText("99+");
        mBubbleDragView.setOnBubbleStateListener(new BubbleDragView.OnBubbleStateListener() {
            @Override
            public void onDrag() {
                XLog.e("拖拽气泡");
            }

            @Override
            public void onMove() {
                XLog.e("移动气泡");
            }

            @Override
            public void onRestore() {
                XLog.e("气泡恢复原来的位置");
            }

            @Override
            public void onDismiss() {
                XLog.e("气泡消失");
            }
        });

    }

    public void doReCreate(View view) {
        mBubbleDragView.reCreate();
    }

}
