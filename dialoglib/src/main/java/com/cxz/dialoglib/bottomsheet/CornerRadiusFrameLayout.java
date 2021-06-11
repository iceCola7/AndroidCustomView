package com.cxz.dialoglib.bottomsheet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author chenxz
 * @date 2019/3/19
 * @desc 圆角界面
 */
public class CornerRadiusFrameLayout extends FrameLayout {

    private boolean noCornerRadius = true;
    private Path path = new Path();
    private RectF rect = new RectF();
    private float[] outerRadii = {
            // Top left corner
            0f, 0f,
            // Top right corner
            0f, 0f,
            // Bottom right corner
            0f, 0f,
            // Bottom left corner
            0f, 0f
    };

    public CornerRadiusFrameLayout(Context context) {
        super(context);
        initView();
    }

    public CornerRadiusFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CornerRadiusFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        rect.set(0f, 0f, w, h);
        resetPath();
    }

    @Override
    public void draw(Canvas canvas) {
        if (noCornerRadius) {
            super.draw(canvas);
        } else {
            if (canvas == null) return;
            int save = canvas.save();
            canvas.clipPath(this.path);
            super.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    public void setCornerRadius(Float radius) {
        // Top left corner
        outerRadii[0] = radius;
        outerRadii[1] = radius;

        // Top right corner
        outerRadii[2] = radius;
        outerRadii[3] = radius;

        noCornerRadius = (radius == 0f);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        resetPath();
        invalidate();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void resetPath() {
        path.reset();
        path.addRoundRect(rect, outerRadii, Path.Direction.CW);
        path.close();
    }

}
