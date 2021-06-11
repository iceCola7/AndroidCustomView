package com.cxz.explosionlib.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author admin
 * @date 2019/9/26
 * @desc 粒子的抽象类
 */
public abstract class Particle {

    float cx;
    float cy;
    int color;

    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
    }

    // 计算
    protected abstract void calculate(float factor);

    // 绘制
    protected abstract void draw(Canvas canvas, Paint paint);

    // 逐帧绘制
    public void advance(Canvas canvas, Paint paint, float factor) {
        calculate(factor);
        draw(canvas, paint);
    }

}
