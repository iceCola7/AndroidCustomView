package com.cxz.explosionlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import com.cxz.explosionlib.factory.ParticleFactory;
import com.cxz.explosionlib.util.Utils;

import java.util.ArrayList;

/**
 * @author admin
 * @date 2019/9/26
 * @desc 动画场地
 */
public class ExplosionField extends View {

    // 同时执行多个动画
    private ArrayList<ExplosionAnimator> explosionAnimators;

    // 粒子工厂（去生产不同的粒子）
    private ParticleFactory mParticleFactory;
    // 需要实现爆炸效果的控件
    private OnClickListener onClickListener;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        explosionAnimators = new ArrayList<>();
        mParticleFactory = particleFactory;
        // 添加动画到Activity
        attach2Activity();
    }

    private void attach2Activity() {
        // 找到 DecorView 添加到当前布局
        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, lp);
    }

    // 添加监听
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnclickListener());
        }
    }

    private OnClickListener getOnclickListener() {
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 触发爆炸效果
                    explode(v);
                }
            };
        }
        return onClickListener;
    }

    /**
     * 触发爆炸效果
     */
    private void explode(final View view) {
        // 先去获取控件的位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        if (rect.width() == 0 || rect.height() == 0) {
            return;
        }
        // 震动的动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 开始粒子爆炸效果
                explosion2(view, rect);
            }
        });
        animator.start();
    }

    private void explosion2(final View view, Rect rect) {
        // 开启粒子动画
        final ExplosionAnimator animator = new ExplosionAnimator(this, Utils.createBitmapFromView(view),
                rect, mParticleFactory);
        explosionAnimators.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setClickable(false);
                view.animate().setDuration(150).scaleX(0).scaleY(0).alpha(0).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
                view.animate().setDuration(150).scaleX(1).scaleY(1).alpha(1).start();
                // 移除
                explosionAnimators.remove(animator);
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }
}
