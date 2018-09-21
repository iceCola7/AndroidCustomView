package com.cxz.tilt.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author chenxz
 * @date 2018/9/19
 * @desc 倾斜的 TextView
 */
public class TiltTextView extends View {

    private static final int MODE_LEFT_TOP = 0;
    private static final int MODE_LEFT_TOP_TRIANGLE = 1;

    public static final int ROTATE_ANGLE = 45;

    private Paint mPaint;
    private Paint mTextPaint;
    private int mTiltBgColor = Color.TRANSPARENT;
    private int mTextColor = Color.WHITE;
    private float mTextSize = 16;
    private float mTiltLength = 40;
    private String mTiltText = "";
    private int mMode = MODE_LEFT_TOP;

    public TiltTextView(Context context) {
        this(context, null);
    }

    public TiltTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TiltTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TiltTextView);
        mTiltBgColor = array.getColor(R.styleable.TiltTextView_tiltBgColor, mTiltBgColor);
        mTextSize = array.getDimension(R.styleable.TiltTextView_tiltTextSize, mTextSize);
        mTextColor = array.getColor(R.styleable.TiltTextView_tiltTextColor, mTextColor);
        mTiltLength = array.getDimension(R.styleable.TiltTextView_tiltLength, mTiltLength);
        if (array.hasValue(R.styleable.TiltTextView_tiltText)) {
            mTiltText = array.getString(R.styleable.TiltTextView_tiltText);
        }
        if (array.hasValue(R.styleable.TiltTextView_tiltMode)) {
            mMode = array.getInt(R.styleable.TiltTextView_tiltMode, MODE_LEFT_TOP);
        }
        array.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTiltBgColor);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawText(canvas);
    }

    /**
     * 绘制控件背景
     */
    private void drawBg(Canvas canvas) {
        Path path = new Path();
        int w = getWidth();
        int h = getHeight();

        if (w != h) throw new IllegalStateException("TiltTextView's width must equal to height");

        switch (mMode) {
            case MODE_LEFT_TOP:
                path = getModeLeftTopPath(path, w, h);
                break;
            case MODE_LEFT_TOP_TRIANGLE:
                path = getModeLeftTopTrianglePath(path, w, h);
                break;
        }

        path.close();
        canvas.drawPath(path, mPaint);
        canvas.save();
    }

    private Path getModeLeftTopPath(Path path, int w, int h) {
        path.moveTo(w, 0);
        path.lineTo(0, h);
        path.lineTo(0, h - mTiltLength);
        path.lineTo(w - mTiltLength, 0);
        return path;
    }

    private Path getModeLeftTopTrianglePath(Path path, int w, int h) {
        path.lineTo(0, h);
        path.lineTo(w, 0);
        return path;
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        int w = (int) (canvas.getWidth() - mTiltLength / 2);
        int h = (int) (canvas.getHeight() - mTiltLength / 2);
        float xy[] = calculateXY(canvas, w, h);

        float toX = xy[0];
        float toY = xy[1];
        float centerX = xy[2];
        float centerY = xy[3];
        float angle = xy[4];

        canvas.rotate(angle, centerX, centerY);

        canvas.drawText(mTiltText, toX, toY, mTextPaint);
    }

    private float[] calculateXY(Canvas canvas, int w, int h) {
        float[] xy = new float[5];
        Rect rect = null;
        RectF rectF = null;
        int offset = (int) (mTiltLength / 2);

        switch (mMode) {
            case MODE_LEFT_TOP:
            case MODE_LEFT_TOP_TRIANGLE:
                rect = new Rect(0, 0, w, h);
                rectF = new RectF(rect);
                rectF.right = mTextPaint.measureText(mTiltText, 0, mTiltText.length());
                rectF.bottom = mTextPaint.descent() - mTextPaint.ascent();
                rectF.left += (rect.width() - rectF.right) / 2.0f;
                rectF.top += (rect.height() - rectF.bottom) / 2.0f;
                xy[0] = rectF.left;
                xy[1] = rectF.top - mTextPaint.ascent();
                xy[2] = w / 2;
                xy[3] = h / 2;
                xy[4] = -ROTATE_ANGLE;
                break;
        }
        return xy;
    }


}
