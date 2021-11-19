package com.cxz.androidcustomview.activity.bottomdialog;

import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;

public class BottomDialogActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_bottom_dialog;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        findViewById(R.id.btn_share_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareBottomDialog().show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.btn_edit_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditBottomDialog().show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.btn_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomSheetDialog dialog = new TestBottomSheetDialog();
                dialog.show(getSupportFragmentManager(), "bottom_sheet_dialog");
            }
        });
    }

}