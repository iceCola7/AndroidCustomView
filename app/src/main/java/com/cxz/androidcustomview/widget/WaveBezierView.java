package com.cxz.androidcustomview.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenxz on 2018/8/4.
 */
public class WaveBezierView extends View {

    private Paint mPaint;

    public WaveBezierView(Context context) {
        this(context, null);
    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
