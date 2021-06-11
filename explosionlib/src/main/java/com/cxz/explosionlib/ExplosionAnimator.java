package com.cxz.explosionlib;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.cxz.explosionlib.factory.ParticleFactory;
import com.cxz.explosionlib.particle.Particle;

/**
 * @author admin
 * @date 2019/9/26
 * @desc 粒子动画控制器（控制动画的进度）
 */
public class ExplosionAnimator extends ValueAnimator {

    public static int default_duration = 1500;

    // 粒子
    private Particle[][] mParticles;
    // 粒子工厂
    private ParticleFactory mParticleFactory;
    // 动画场地
    private View mContainer;
    // 画笔
    private Paint mPaint;

    public ExplosionAnimator(View mContainer, Bitmap bitmap, Rect bound, ParticleFactory mParticleFactory) {
        this.mParticleFactory = mParticleFactory;
        this.mContainer = mContainer;
        mPaint = new Paint();
        setFloatValues(0F, 1F);
        setDuration(default_duration);
        mParticles = this.mParticleFactory.generateParticles(bitmap, bound);
    }

    // 绘制
    public void draw(Canvas canvas) {
        if (!isStarted()) {
            // 动画绘制关闭
            return;
        }
        // 所有粒子开始运动
        for (Particle[] mParticle : mParticles) {
            for (Particle particle : mParticle) {
                particle.advance(canvas, mPaint, (Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
