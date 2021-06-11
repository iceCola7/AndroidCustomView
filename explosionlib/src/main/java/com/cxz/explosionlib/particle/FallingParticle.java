package com.cxz.explosionlib.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.cxz.explosionlib.factory.FallingParticleFactory;
import com.cxz.explosionlib.util.Utils;

/**
 * @author admin
 * @date 2019/9/26
 * @desc
 */
public class FallingParticle extends Particle {

    private float radius = FallingParticleFactory.PATH_WH;
    private float alpha = 1.0f;
    private Rect mBound; // 控件区域（主要获取控件宽高改变x,y的位置）

    public FallingParticle(float cx, float cy, int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;
    }

    @Override
    protected void calculate(float factor) {

        cx = cx + factor * Utils.RANDOM.nextInt(mBound.width())
                * (Utils.RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * Utils.RANDOM.nextInt(mBound.height() / 2);

        radius = radius - factor * Utils.RANDOM.nextInt(2);
        alpha = (1f - factor) * (1 + Utils.RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius / 2, paint);
    }
}
