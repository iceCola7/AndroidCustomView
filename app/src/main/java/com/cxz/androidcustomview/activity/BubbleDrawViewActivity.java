package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.util.XLog;
import com.cxz.bubbledragview.BubbleDragView;

public class BubbleDrawViewActivity extends AppCompatActivity {

    private static final String TAG = BubbleDrawViewActivity.class.getName();

    private BubbleDragView mBubbleDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_draw_view);

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
