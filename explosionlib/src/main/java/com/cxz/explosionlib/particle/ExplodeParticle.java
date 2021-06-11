package com.cxz.explosionlib.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cxz.explosionlib.factory.ExplodeParticleFactory;

/**
 * @author chenxz
 * @date 2019/9/26
 * @desc
 */
public class ExplodeParticle extends Particle {
    public float radius = ExplodeParticleFactory.PART_WH;
    public float alpha;
    public int color;
    public float cx;
    public float cy;
    public float baseCx;
    public float baseCy;
    public float baseRadius;
    public float top;
    public float bottom;
    public float mag;
    public float neg;
    public float life;
    public float overflow;

    public ExplodeParticle(float x, float y, int color) {
        super(x, y, color);
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    protected void calculate(float factor) {
        float f = 0f;
        float normalization = factor / ExplodeParticleFactory.END_VALUE;
        if (normalization < life || normalization > 1f - overflow) {
            alpha = 0f;
            return;
        }
        normalization = (normalization - life) / (1f - life - overflow);
        float f2 = normalization * ExplodeParticleFactory.END_VALUE;
        if (normalization >= 0.7f) {
            f = (normalization - 0.7f) / 0.3f;
        }
        alpha = 1f - f;
        f = bottom * f2;
        cx = baseCx + f;
        cy = (float) (baseCy - this.neg * Math.pow(f, 2.0)) - f * mag;
        radius = ExplodeParticleFactory.V + (baseRadius - ExplodeParticleFactory.V) * f2;
    }

}
