package com.cxz.roll.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author chenxz
 * @date 2018/9/24
 * @desc
 */
public class RollTextView extends RelativeLayout {
    public RollTextView(Context context) {
        this(context, null);
    }

    public RollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

    }
}
