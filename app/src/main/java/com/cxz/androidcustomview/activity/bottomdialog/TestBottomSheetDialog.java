package com.cxz.androidcustomview.activity.bottomdialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxz.androidcustomview.R;
import com.cxz.dialoglib.bottomsheet.BaseBottomSheetFragment;

/**
 * @author chenxz
 * @date 2019/3/19
 * @desc
 */
public class TestBottomSheetDialog extends BaseBottomSheetFragment {

    @Override
    protected View attachView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
    }

    @Override
    protected float getCornerRadius() {
        return 32;
    }

    @Override
    protected int getStatusBarColor() {
        return Color.RED;
    }

}
