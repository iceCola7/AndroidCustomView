package com.cxz.androidcustomview.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BliConstraintLayout extends ConstraintLayout {

    private float rotateY = 0;

    private boolean isLeftRotate = true;

    private Matrix matrix = new Matrix();

    private Camera camera = new Camera();

    public BliConstraintLayout(@NonNull Context context) {
        this(context, null);
    }

    public BliConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BliConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //camera保存状态
        camera.save();
        //camera设置绕Y轴旋转角度
        camera.rotateY(rotateY);
        //将变换应用到canvas上
        camera.applyToCanvas(canvas);
        camera.getMatrix(matrix);
        if (!isLeftRotate) {
            //设置靠左进行旋转
            matrix.preTranslate(-getWidth(), -getHeight() >> 1);
            matrix.postTranslate(getWidth(), getHeight() >> 1);
        } else {
            //设置靠右进行旋转
            matrix.preTranslate(0, -getHeight() >> 1);
            matrix.postTranslate(0, getHeight() >> 1);
        }
        canvas.setMatrix(matrix);
        //camera恢复状态
        camera.restore();
        super.dispatchDraw(canvas);
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
        invalidate();
    }

    public void setIsLeftRotate(boolean isLeft) {
        this.isLeftRotate = isLeft;
    }
}
