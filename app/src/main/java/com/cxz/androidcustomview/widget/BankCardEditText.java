package com.cxz.androidcustomview.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

/**
 * Created by chenxz on 2018/8/3.
 */
public class BankCardEditText extends AppCompatEditText {

    public BankCardEditText(Context context) {
        this(context, null);
    }

    public BankCardEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BankCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        setFocusable(true);
        setEnabled(true);
        setFocusableInTouchMode(true);
        BankCardTextWatcher.bind(this);
    }
}

