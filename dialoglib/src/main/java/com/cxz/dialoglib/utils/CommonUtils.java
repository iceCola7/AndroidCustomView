package com.cxz.dialoglib.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;

import com.cxz.dialoglib.R;

/**
 * @author chenxz
 * @date 2019/3/19
 * @desc 工具类
 */
public class CommonUtils {

    public static int calculateColor(int to, float ratio) {
        int alpha = (int) (255 - (255 * ratio));
        return Color.argb(alpha, Color.red(to), Color.green(to), Color.blue(to));
    }

    public static boolean isTablet() {
        return false;
    }

    public static boolean isInPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

}
