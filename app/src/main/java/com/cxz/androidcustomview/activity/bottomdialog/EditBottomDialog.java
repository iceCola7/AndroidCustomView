package com.cxz.androidcustomview.activity.bottomdialog;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cxz.androidcustomview.R;
import com.cxz.dialoglib.bottom.BaseBottomDialog;

/**
 * @author chenxz
 * @date 2018/9/10
 * @desc EditBottomDialog
 */
public class EditBottomDialog extends BaseBottomDialog {

    private EditText editText;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_edit_bottom_dialog;
    }

    @Override
    public void bindView(View v) {
        editText = v.findViewById(R.id.edit_text);
        editText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.showSoftInput(editText, 0);
            }
        });
    }

}
