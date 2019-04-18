package com.cxz.rangeseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.cxz.rangeseekbar.SeekBarUtil.binarySearch;
import static com.cxz.rangeseekbar.SeekBarUtil.dp2px;
import static com.cxz.rangeseekbar.SeekBarUtil.sp2px;

public class RangeSeekBar extends View {

    private static final String TAG = "RangeSeekBar";

    private int DEFAULT_DRAWABLE_RES = R.drawable.range_seek_thumb;
    private int DEFAULT_ACTIVE_COLOR = Color.parseColor("#00aec5");
    private int DEFAULT_INACTIVE_COLOR = Color.parseColor("#bce0fd");
    private int DEFAULT_TEXT_COLOR = Color.parseColor("#333333");

    private enum Thumb {
        MIN, MAX
    }

    private Paint mPaint;

    private Bitmap mThumbImage;

    private float mThumbHalfWidth = 0f;
    private float mThumbHalfHeight = 0f;
    private float mLineHalfHeight = 0f;

    private Thumb mPressedThumb;

    private float mSectionTextSize;

    private int mSectionTextColor;
    private int mTrackActiveColor;
    private int mTrackInactiveColor;
    private int mTrackCircleColor;

    private double mSelectedMinValue = 0.0;
    private double mSelectedMaxValue = 1.0;

    private int mSectionCount = 10;
    // 选择器的最小值
    private int mMinRange = 0;
    // 选择器的最大值
    private int mMaxRange = 100;
    // 已选择的最小值
    private int mMinValue;
    // 已选择的最大值
    private int mMaxValue;
    private int xOffset;

    private int WIDTH;
    private int HEIGHT;

    private SparseIntArray mSectionArray = new SparseIntArray();
    private SparseArray<Double> mSectionRangeArray = new SparseArray<>();

    private boolean isAutoAdjustSection = true;
    private boolean hasRule = false;

    private OnRangeSeekBarListener mOnRangeSeekBarListener;

    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar, defStyleAttr, 0);

        Drawable drawable = a.getDrawable(R.styleable.RangeSeekBar_thumb);
        mSectionTextSize = a.getDimension(R.styleable.RangeSeekBar_sectionTextSize, sp2px(15));
        mSectionTextColor = a.getColor(R.styleable.RangeSeekBar_sectionTextColor, DEFAULT_TEXT_COLOR);
        mTrackActiveColor = a.getColor(R.styleable.RangeSeekBar_trackActiveColor, DEFAULT_ACTIVE_COLOR);
        mTrackInactiveColor = a.getColor(R.styleable.RangeSeekBar_trackInactiveColor, DEFAULT_INACTIVE_COLOR);
        mTrackCircleColor = a.getColor(R.styleable.RangeSeekBar_trackCircleColor, DEFAULT_INACTIVE_COLOR);
        a.recycle();

        drawable = (drawable != null) ? drawable : context.getResources().getDrawable(DEFAULT_DRAWABLE_RES);
        mThumbImage = SeekBarUtil.drawable2Bitmap(drawable);
        if (mThumbImage != null) {
            mThumbHalfWidth = 0.5f * mThumbImage.getWidth();
            mThumbHalfHeight = 0.5f * mThumbImage.getHeight();
            mLineHalfHeight = 0.25f * mThumbHalfHeight;
        }
        xOffset = (int) mThumbHalfWidth + dp2px(10);
        mMinValue = mMinRange;
        mMaxValue = mMaxRange;

//        int sectionRange = (mMaxRange - mMinRange) / mSectionCount;
//        for (int i = 0; i < mSectionCount + 1; i++) {
//            mSectionArray.put(i, i * sectionRange + mMinRange);
//        }

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSectionArray.size() == 0) return;

        // draw seek bar background line
        mPaint.setColor(mTrackInactiveColor);
        canvas.drawRect(xOffset, mThumbHalfHeight - mLineHalfHeight,
                WIDTH - xOffset, mThumbHalfHeight + mLineHalfHeight, mPaint);

        // draw seek bar active range line
        mPaint.setColor(mTrackActiveColor);
        canvas.drawRect((float) valueToScreen(mSelectedMinValue), mThumbHalfHeight - mLineHalfHeight,
                (float) valueToScreen(mSelectedMaxValue), mThumbHalfHeight + mLineHalfHeight, mPaint);

        // draw rule
        if (hasRule) drawRule(canvas);

        // draw section text
        drawSectionText(canvas);

        // draw thumb
        drawThumb(canvas);

        // draw the text
//        drawText(canvas);

    }

    private void drawSectionText(Canvas canvas) {
        for (int i = 0; i < mSectionCount + 1; i++) {
            if (mMinValue == mSectionArray.get(i) || mMaxValue == mSectionArray.get(i)) {
                mPaint.setTextSize(mSectionTextSize + sp2px(2));
                mPaint.setColor(mTrackActiveColor);
            } else {
                mPaint.setTextSize(mSectionTextSize);
                mPaint.setColor(mSectionTextColor);
            }
            canvas.drawText(String.valueOf(mSectionArray.get(i)), mSectionRangeArray.get(i).floatValue(),
                    2 * mSectionTextSize + 2 * mThumbHalfHeight, mPaint);
        }
    }

    private void drawRule(Canvas canvas) {
        mPaint.setColor(mTrackCircleColor);
        for (int i = 0; i < mSectionCount + 1; i++) {
            canvas.drawCircle(mSectionRangeArray.get(i).floatValue(), mThumbHalfHeight,
                    mThumbHalfHeight / 2, mPaint);
        }
    }

    private void drawThumb(Canvas canvas) {
        canvas.drawBitmap(mThumbImage,
                (float) valueToScreen(mSelectedMinValue) - mThumbHalfWidth, 0f, mPaint);
        canvas.drawBitmap(mThumbImage,
                (float) valueToScreen(mSelectedMaxValue) - mThumbHalfWidth, 0f, mPaint);
    }

    private void drawText(Canvas canvas) {

        String minText = String.valueOf((int) (mSelectedMinValue * (mMaxRange - mMinRange)) + mMinRange);
        String maxText = String.valueOf((int) (mSelectedMaxValue * (mMaxRange - mMinRange)) + mMinRange);

        float minTextWidth = mPaint.measureText(minText);
        float maxTextWidth = mPaint.measureText(maxText);

        double minTextX = valueToScreen(mSelectedMinValue); // - minTextWidth * 0.5f;
        if (minTextX < 0) minTextX = 0.0;

        double maxTextX = valueToScreen(mSelectedMaxValue); // - maxTextWidth * 0.5f;
        if (maxTextX + maxTextWidth > WIDTH) {
            maxTextX = WIDTH - xOffset; // (WIDTH - maxTextWidth);
        }

        float minTextYOffset = 0f;
        float maxTextYOffset = 0f;
        if (minTextX + minTextWidth >= maxTextX) {
            minTextYOffset = -mSectionTextSize / 1.5f;
            maxTextYOffset = mSectionTextSize / 1.5f;
        }

        mPaint.setColor(mTrackActiveColor);
        mPaint.setTextSize(mSectionTextSize + sp2px(2));
        canvas.drawText(minText, (float) minTextX,
                2 * mSectionTextSize + 2 * mThumbHalfHeight + minTextYOffset, mPaint);
        canvas.drawText(maxText, (float) maxTextX,
                2 * mSectionTextSize + 2 * mThumbHalfHeight + maxTextYOffset, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMyMeasureWidth(widthMeasureSpec);
        int height = getMyMeasureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

    }

    private int getMyMeasureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = (int) (2 * mSectionTextSize + 3 * mThumbHalfHeight);
        if (mode == MeasureSpec.EXACTLY) {
            size = Math.max(size, height);
        } else {
            // wrap content
            size = Math.min(size, height);
        }
        return size;
    }

    private int getMyMeasureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 2 * xOffset;
        if (mode == MeasureSpec.EXACTLY) {
            size = Math.max(size, width);
        } else {
            // wrap content
            size = Math.min(size, width);
        }
        return size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        WIDTH = w;
        HEIGHT = h;

        if (mSectionArray.size() == 0) return;

        mSectionRangeArray.clear();
        double degX = (float) (WIDTH - xOffset * 2) / mSectionCount;
        double j = (double) xOffset;
        for (int i = 0; i < mSectionCount + 1; i++, j += degX) {
            mSectionRangeArray.put(i, j);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mPressedThumb = evalPressedThumb(event.getX());
                // Only handle thumb presses.
                if (mPressedThumb == null) {
                    return super.onTouchEvent(event);
                }
                trackTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPressedThumb != null) {
                    trackTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);

                cancelTouchEvent(event);

                mPressedThumb = null;
                break;
        }
        return true;
    }

    private void cancelTouchEvent(MotionEvent event) {
        if (isAutoAdjustSection) {
            float x = event.getX();

            int index = binarySearch(mSectionRangeArray, x);
            double screenX = screenToValue(mSectionRangeArray.get(index));

            // 只保留两位小数
            BigDecimal bigDecimal = new BigDecimal(screenX);
            screenX = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            if (Thumb.MIN == mPressedThumb) {
                changeValue(screenX, mSelectedMaxValue);
            } else if (Thumb.MAX == mPressedThumb) {
                changeValue(mSelectedMinValue, screenX);
            }
        }

        int minIndex = binarySearch(mSectionRangeArray, valueToScreen(mSelectedMinValue));
        int maxIndex = binarySearch(mSectionRangeArray, valueToScreen(mSelectedMaxValue));

        mMinValue = mSectionArray.get(minIndex);
        mMaxValue = mSectionArray.get(maxIndex);

        postInvalidate();

        if (mOnRangeSeekBarListener != null) {
            mOnRangeSeekBarListener.onRangeChange(mMinValue, mMaxValue);
        }
    }

    private void trackTouchEvent(MotionEvent event) {
        float x = event.getX();
        double screenX = screenToValue(x);
        if (Thumb.MIN == mPressedThumb) {
            changeValue(screenX, mSelectedMaxValue);
        } else if (Thumb.MAX == mPressedThumb) {
            changeValue(mSelectedMinValue, screenX);
        }
        postInvalidate();
    }

    private void changeValue(double first, double second) {
        if (first > second) {
            if (mSelectedMinValue == first) {
                mSelectedMaxValue = mSelectedMinValue;
            } else if (mSelectedMaxValue == second) {
                mSelectedMinValue = mSelectedMaxValue;
            }
        } else {
            mSelectedMinValue = first;
            mSelectedMaxValue = second;
        }
        if (mSelectedMinValue < 0) {
            mSelectedMinValue = 0.0;
        }
        if (mSelectedMinValue > 1) {
            mSelectedMinValue = 1.0;
        }
        if (mSelectedMaxValue < 0) {
            mSelectedMaxValue = 0.0;
        }
        if (mSelectedMaxValue > 1) {
            mSelectedMaxValue = 1.0;
        }
    }

    private double screenToValue(double screenX) {
        if (WIDTH == 0) return 0.0;
        double result = (screenX - xOffset) / (WIDTH - 2 * xOffset);
        return Math.min(1f, Math.max(0f, result));
    }

    private double valueToScreen(double value) {
        return xOffset + value * (WIDTH - 2 * xOffset);
    }

    private Thumb evalPressedThumb(float touchX) {
        Thumb result = null;
        boolean minThumbPressed = isInThumbRange(touchX, mSelectedMinValue);
        boolean maxThumbPressed = isInThumbRange(touchX, mSelectedMaxValue);
        if (minThumbPressed && maxThumbPressed) {
            result = (touchX / WIDTH > 0.5f) ? Thumb.MIN : Thumb.MAX;
        } else if (minThumbPressed) {
            result = Thumb.MIN;
        } else if (maxThumbPressed) {
            result = Thumb.MAX;
        }
        return result;
    }

    private boolean isInThumbRange(float touchX, double normalizedThumbValue) {
        return Math.abs(touchX - valueToScreen(normalizedThumbValue)) <= 2 * mThumbHalfWidth;
    }

    /**************************************  interface  *******************************************/

    public interface OnRangeSeekBarListener {
        void onRangeChange(int minValue, int maxValue);
    }

    /**************************************  API start  *******************************************/

    public void setSectionList(List<Integer> list) {
        if (list.size() == 0) return;
        Collections.sort(list);
        mSectionArray.clear();
        for (int i = 0; i < list.size(); i++) {
            mSectionArray.put(i, list.get(i));
        }
        mSectionCount = mSectionArray.size() - 1;
        mMinRange = mMinValue = list.get(0);
        mMaxRange = mMaxValue = list.get(mSectionCount);
        postInvalidate();
    }

    public void setOnRangeSeekBarListener(OnRangeSeekBarListener onRangeSeekBarListener) {
        this.mOnRangeSeekBarListener = onRangeSeekBarListener;
    }

    /**************************************  API end  *********************************************/

}
