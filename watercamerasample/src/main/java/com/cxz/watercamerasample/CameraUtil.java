package com.cxz.watercamerasample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author admin
 * @date 2019/9/17
 * @desc
 */
public class CameraUtil {

    private static String TAG = "CameraUtil";

    private static String storagePath = "";

    /**
     * 初始化保存路径
     */
    public static String getPath() {
        String path;
        if (storagePath.equals("")) {
            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zf/";
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        path = storagePath + format.format(new Date()) + ".png";
        return path;
    }

    /**
     * 格式化时间
     */
    public static String formatDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 旋转bitmap
     */
    public static Bitmap rotateBitmap(Bitmap source, int degree, boolean flipHorizontal, boolean recycle) {
        if (degree == 0 && !flipHorizontal) {
            return source;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        if (flipHorizontal) {
            matrix.postScale(-1, 1);
        }
        Log.i(TAG, "source width: " + source.getWidth() + ", height: " + source.getHeight());
        Log.i(TAG, "rotateBitmap: degree: " + degree);
        Bitmap rotateBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
        Log.i(TAG, "rotate width: " + rotateBitmap.getWidth() + ", height: " + rotateBitmap.getHeight());
        if (recycle) {
            source.recycle();
        }
        return rotateBitmap;
    }

    /**
     * 保存Bitmap到sdcard
     */
    public static void saveBitmap(Bitmap bm, String path, Context context) {
        try {
            // 保存图片
            Bitmap targetImg;
            targetImg = addWaterBitmap(bm, null, formatDate());

            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            targetImg.compress(Bitmap.CompressFormat.JPEG, 99, bos);//嘿嘿 99的效果还是很好的
            bos.flush();
            bos.close();

            if (bm != null) {
                bm.recycle();
                System.gc();
            }
            targetImg.recycle();
            System.gc();
            //刷新系统相册，刷新系统相册的操作实际上就是发送了系统内置的广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(path));
            intent.setData(uri);
            context.sendBroadcast(intent);
            Log.i(TAG, "saveBitmap成功");
        } catch (Exception e) {
            Log.e(TAG, "saveBitmap:失败");
            e.printStackTrace();
        }
    }

    /**
     * 添加水印
     *
     * @param src      原图片
     * @param waterMak 水印图片
     * @param title    水印文字
     * @return
     */
    public static Bitmap addWaterBitmap(Bitmap src, Bitmap waterMak, String title) {
        if (src == null) {
            return src;
        }
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        canvas.drawBitmap(src, 0, 0, null);
        // 图片水印
        if (waterMak != null) {
            int ww = waterMak.getWidth();
            int wh = waterMak.getHeight();
            // 在src的右下角添加水印
            Paint paint = new Paint();
            paint.setAlpha(100);
            canvas.drawBitmap(waterMak, w - ww - 20, h - wh - 20, paint);
        }
        // 文字水印
        if (null != title) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.parseColor("#FFFFFF"));
            int s;
            if (w > h) {
                s = h / 20;
            } else {
                s = w / 20;
            }
            textPaint.setTextSize(s);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(title, w / 2, h - s, textPaint);
        }
        canvas.save();
        canvas.restore();
        return newBitmap;
    }
}
