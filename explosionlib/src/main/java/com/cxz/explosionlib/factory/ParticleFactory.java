package com.cxz.explosionlib.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.cxz.explosionlib.particle.Particle;

/**
 * @author admin
 * @date 2019/9/26
 * @desc 粒子工厂，用来生成粒子
 */
public abstract class ParticleFactory {

    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);

}
