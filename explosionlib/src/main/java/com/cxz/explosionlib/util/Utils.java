package com.cxz.explosionlib.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * @author admin
 * @date 2019/9/26
 * @desc
 */
public class Utils {

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    private Utils() {
    }

    public static int dip2px(int dp) {
        return Math.round(dp * DENSITY);
    }

    /**
     * 从视图获得它的图像
     *
     * @param view 要爆炸的view
     * @return 它的图像
     */
    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus(); // 使View失去焦点恢复原本样式
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (CANVAS) {
                Canvas canvas = CANVAS;
                canvas.setBitmap(bitmap);
                view.draw(canvas); // 使用View的绘制方法生产View的备份
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     * 创建一个和指定尺寸大小一样的bitmap
     *
     * @param width      宽
     * @param height     高
     * @param config     快照配置 详见 {@link Bitmap.Config}
     * @param retryCount 当生成空白bitmap发生oom时  我们会尝试再试试生成bitmap 这个为尝试的次数
     * @return 一个和指定尺寸大小一样的bitmap
     */
    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            // 创建空白的bitmap
            return Bitmap.createBitmap(width, height, config);
            // 如果发生了oom
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                // 主动gc 然后再次试试
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            // 直到次数用光
            return null;
        }
    }

}
