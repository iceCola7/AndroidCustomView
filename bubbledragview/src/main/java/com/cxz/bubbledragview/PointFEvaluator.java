package com.cxz.bubbledragview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author chenxz
 * @date 2019/3/3
 * @desc PointF动画估值器
 */
public class PointFEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        return new PointF(x, y);
    }

}
