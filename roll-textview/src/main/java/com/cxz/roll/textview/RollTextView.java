package com.cxz.roll.textview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author chenxz
 * @date 2018/9/24
 * @desc
 */
public class RollTextView extends LinearLayout implements BaseRollAdapter.OnDataChangedListener {

    private static final int DEFAULT_GAP = 4000;
    private static final int DEFAULT_ANIM_DURATION = 1000;
    private float mRollTVHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
    private int mGap = DEFAULT_GAP;
    private int mAnimDuration = DEFAULT_ANIM_DURATION;
    private BaseRollAdapter mAdapter;
    private Paint mPaint;

    private View mFirstView;
    private View mSecondView;

    private int mPosition = 0;
    private boolean isStarted = false;
    private AnimRunnable mRunnable = new AnimRunnable();

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
        setOrientation(VERTICAL);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RollTextView);
        mGap = array.getInteger(R.styleable.RollTextView_gap, mGap);
        mAnimDuration = array.getInteger(R.styleable.RollTextView_anim_duration, mAnimDuration);

        if (mGap <= mAnimDuration) {
            mGap = DEFAULT_GAP;
            mAnimDuration = DEFAULT_ANIM_DURATION;
        }
        array.recycle();
    }

    /**
     * 设置适配器
     */
    public void setAdapter(BaseRollAdapter adapter) {
        if (adapter == null) {
            throw new RuntimeException("adapter must be not null");
        }
        if (mAdapter != null) {
            throw new RuntimeException("you have already set an adapter");
        }
        this.mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
        setupAdapter();
    }

    private void setupAdapter() {
        removeAllViews();
        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);
        } else {
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));
            addView(mFirstView);
            addView(mSecondView);

            mPosition = 1;
            isStarted = false;
        }
        setBackgroundDrawable(mFirstView.getBackground());
    }

    /**
     * 开始
     */
    public void start() {
        if (mAdapter == null) {
            throw new RuntimeException("you must call setAdapter() before start");
        }
        if (!isStarted && mAdapter.getCount() > 1) {
            isStarted = true;
            postDelayed(mRunnable, mGap);
        }
    }

    /**
     * 结束
     */
    public void stop() {
        removeCallbacks(mRunnable);
        isStarted = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = (int) mRollTVHeight;
        } else {
            mRollTVHeight = getHeight();
        }
        if (isInEditMode()) {
            setBackgroundColor(Color.GRAY);
            return;
        }
        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = (int) mRollTVHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = (int) mRollTVHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawText("RollTextView", 20, getHeight() * 2 / 3, mPaint);
        }
    }

    @Override
    public void onChanged() {
        setupAdapter();
    }

    private void performSwitch() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() - mRollTVHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY() - mRollTVHeight);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removedView = getChildAt(0);
                mPosition++;
                mAdapter.setItem(removedView, mAdapter.getItem(mPosition % mAdapter.getCount()));
                removeView(removedView);
                addView(removedView, 1);
            }

        });
        set.setDuration(mAnimDuration);
        set.start();
    }

    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            performSwitch();
            postDelayed(this, mGap);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
