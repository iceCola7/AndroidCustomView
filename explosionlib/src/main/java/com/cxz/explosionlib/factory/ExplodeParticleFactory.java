package com.cxz.explosionlib.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.cxz.explosionlib.particle.ExplodeParticle;
import com.cxz.explosionlib.particle.Particle;
import com.cxz.explosionlib.util.Utils;

/**
 * @author chenxz
 * @date 2019/9/26
 * @desc
 */
public class ExplodeParticleFactory extends ParticleFactory {
    public static final int PART_WH = 15; //默认小球宽高
    public static final float X = Utils.dip2px(5);
    public static final float Y = Utils.dip2px(20);
    public static final float V = Utils.dip2px(2);
    public static final float W = Utils.dip2px(1);
    public static final float END_VALUE = 1.4f;
    private Rect mBound;

    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        mBound = new Rect(bound);
        int w = bound.width();
        int h = bound.height();
        int partW_Count = w / PART_WH; //横向个数
        int partH_Count = h / PART_WH; //竖向个数

        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;

        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) { //行
            for (int column = 0; column < partW_Count; column++) { //列
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);

                float x = bound.left + ExplodeParticleFactory.PART_WH * column;
                float y = bound.top + ExplodeParticleFactory.PART_WH * row;
                particles[row][column] = generateParticle(color);
            }
        }
        return particles;
    }

    private Particle generateParticle(int color) {
        ExplodeParticle particle = new ExplodeParticle(color, 0, 0);
        particle.color = color;
        particle.radius = V;
        if (Utils.RANDOM.nextFloat() < 0.2f) {
            particle.baseRadius = V + ((X - V) * Utils.RANDOM.nextFloat());
        } else {
            particle.baseRadius = W + ((V - W) * Utils.RANDOM.nextFloat());
        }
        float nextFloat = Utils.RANDOM.nextFloat();
        particle.top = mBound.height() * ((0.18f * Utils.RANDOM.nextFloat()) + 0.2f);
        particle.top = nextFloat < 0.2f ? particle.top : particle.top + ((particle.top * 0.2f) * Utils.RANDOM.nextFloat());
        particle.bottom = (mBound.height() * (Utils.RANDOM.nextFloat() - 0.5f)) * 1.8f;
        float f = nextFloat < 0.2f ? particle.bottom : nextFloat < 0.8f ? particle.bottom * 0.6f : particle.bottom * 0.3f;
        particle.bottom = f;
        particle.mag = 4.0f * particle.top / particle.bottom;
        particle.neg = (-particle.mag) / particle.bottom;
        f = mBound.centerX() + (Y * (Utils.RANDOM.nextFloat() - 0.5f));
        particle.baseCx = f;
        particle.cx = f;
        f = mBound.centerY() + (Y * (Utils.RANDOM.nextFloat() - 0.5f));
        particle.baseCy = f;
        particle.cy = f;
        particle.life = END_VALUE / 10 * Utils.RANDOM.nextFloat();
        particle.overflow = 0.4f * Utils.RANDOM.nextFloat();
        particle.alpha = 1f;
        return particle;
    }
}
