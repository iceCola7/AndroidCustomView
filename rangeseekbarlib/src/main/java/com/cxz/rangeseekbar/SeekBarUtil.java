package com.cxz.rangeseekbar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.SparseArray;
import android.util.TypedValue;

public class SeekBarUtil {

    /**
     * 二分查找：查找数字a在数组array中离哪个数字最近，并返回下标
     */
    public static int binarySearch(SparseArray<Double> array, double a) {
        int size = array.size();
        int lo = 0;
        int hi = size - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (array.get(mid) == a) {
                return mid;
            } else if (array.get(mid) < a) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        if (lo >= size) lo = size - 1;
        if (hi >= size) hi = size - 1;
        if (lo < 0) lo = 0;
        if (hi < 0) hi = 0;

        double loSub = array.get(lo) - a;
        if (loSub < 0) loSub = -loSub;

        double hiSub = array.get(hi) - a;
        if (hiSub < 0) hiSub = -hiSub;

        if (loSub > hiSub) return hi;
        else return lo;
    }

    /**
     * drawable -> bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable ||
                drawable instanceof GradientDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

}
