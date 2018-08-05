package com.cxz.androidcustomview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by chenxz on 2018/8/4.
 */
public class WaveBezierView extends View {

    private static final int SIN = 0;
    private static final int COS = 1;

    private Path mWavePath;
    private Paint mPaint;
    private int mWaveLength;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mWaveCount;
    //波浪流动偏移量
    private int mOffset;
    private int mY;
    private int mWaveColor = Color.LTGRAY;

    private int mWaveType = SIN;

    private ValueAnimator mValueAnimator;

    public WaveBezierView(Context context) {
        this(context, null);
    }

    public WaveBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        initPaint();

        mWavePath = new Path();

        mY = dp2px(10);
        mWaveLength = dp2px(500);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mWaveColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    private void initAnimator() {
        //设置动画运动距离
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setStartDelay(300);
        //设置播放数量无限循环
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 设置线性运动的插值器
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取偏移量，绘制波浪曲线的X横坐标加上此偏移量，产生移动效果
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;

        //此处多加1，是为了预先加载屏幕外的一个波浪，持续报廊移动时的连续性
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mWaveType) {
            case SIN:
                drawSinPath(canvas);
                break;
            case COS:
                drawCosPath(canvas);
                break;
        }

    }

    /**
     * sin 函数的波形
     *
     * @param canvas
     */
    private void drawSinPath(Canvas canvas) {
        mWavePath.reset();

        //Y坐标每次绘制时减去偏移量，即波浪升高
        mWavePath.moveTo(-mWaveLength + mOffset, mY);
        //每次循环绘制两个二阶贝塞尔曲线形成一个完整波形（含有一个上拱圆，一个下拱圆）
        for (int i = 0; i < mWaveCount; i++) {
            //第一个控制点的坐标为(-mWaveLength * 3 / 4,-mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
                    -mY,
                    -mWaveLength / 2 + mOffset + i * mWaveLength,
                    mY);

            //第二个控制点的坐标为(-mWaveLength / 4,3 * mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
                    3 * mY,
                    mOffset + i * mWaveLength,
                    mY);

            //第二种写法：相对位移
//            mWavePath.rQuadTo(mWaveLength / 4, -60, mWaveLength / 2, 0);
//            mWavePath.rQuadTo(mWaveLength / 4, +60, mWaveLength / 2, 0);
        }
        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    /**
     * cos 函数的波形
     *
     * @param canvas
     */
    private void drawCosPath(Canvas canvas) {
        mWavePath.reset();

        //Y坐标每次绘制时减去偏移量，即波浪升高
        mWavePath.moveTo(-mWaveLength + mOffset, mY);
        //每次循环绘制两个二阶贝塞尔曲线形成一个完整波形（含有一个上拱圆，一个下拱圆）
        for (int i = 0; i < mWaveCount; i++) {
            //第一个控制点的坐标为(-mWaveLength * 3 / 4,3 * mWaveAmplitude
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i * mWaveLength,
                    3 * mY,
                    -mWaveLength / 2 + mOffset + i * mWaveLength,
                    mY);

            //第二个控制点的坐标为(-mWaveLength / 4,-mWaveAmplitude)
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i * mWaveLength,
                    -mY,
                    mOffset + i * mWaveLength,
                    mY);
        }
        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public void startWave() {
        initAnimator();
    }

    public void stopWave() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

}
