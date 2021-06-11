package com.cxz.explosionlib.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.cxz.explosionlib.particle.FallingParticle;
import com.cxz.explosionlib.particle.Particle;

/**
 * @author admin
 * @date 2019/9/26
 * @desc
 */
public class FallingParticleFactory extends ParticleFactory {

    public static final int PATH_WH = 14; // 默认小球区域宽高

    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {

        int w = bound.width();
        int h = bound.height();

        int partW_count = w / PATH_WH; // 横向个数（列数）
        int partH_count = h / PATH_WH; // 纵向个数（行数）
        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count; // 列
        int bitmap_part_h = bitmap.getHeight() / partH_count; // 行

        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                // 取得粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                float x = bound.left + PATH_WH * column;
                float y = bound.top + PATH_WH * row;
                // 关联粒子对象
                particles[row][column] = new FallingParticle(x, y, color, bound);
            }
        }
        return particles;
    }
}
