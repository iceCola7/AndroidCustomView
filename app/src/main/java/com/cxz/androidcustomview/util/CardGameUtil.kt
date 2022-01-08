package com.cxz.androidcustomview.util

import android.content.Context
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.widget.Rotate3DAnimation

/**
 * @author chenxz
 * @date 2020/8/5
 * @description 卡牌游戏工具类
 */
object CardGameUtil {

    // 旋转的卡牌是否是正面
    private var isCardFront = false

    /**
     * 旋转卡牌动画
     *
     * @param cardIV    [ImageView] 要旋转的 ImageView
     * @param contentTV [TextView] 旋转完成之后要显示的内容
     */
    fun rotateCardView(context: Context?, cardIV: ImageView, contentTV: TextView) {
        isCardFront = false
        cardIV.post {

            // 选取中心点
            val centerX = cardIV.width / 2f
            val centerY = cardIV.height / 2f
            // 构建3D旋转动画对象，旋转角度为0-90度
            val animation = Rotate3DAnimation(context, 0f, 90f,
                    centerX, centerY, 0f, false)
            // 设置动画时长
            animation.duration = 500
            animation.fillAfter = true
            animation.interpolator = AccelerateInterpolator()
            cardIV.startAnimation(animation)
            // 监听器  翻转到90度的时候 卡面图片改变 然后将卡牌从270度翻转到360度刚好转回来
            // 这里注意不是90-180度,因为90-180翻转过来的图片是左右相反的镜像图
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    isCardFront = !isCardFront
                    // 点正面切换背面，反之亦然
                    if (isCardFront) {
                        cardIV.setImageResource(R.mipmap.icon_card_front_bg_2)
                    } else {
                        cardIV.setImageResource(R.mipmap.icon_card_back_bg_2)
                    }
                    // 270度旋转到360度
                    val rotation = Rotate3DAnimation(context, 270f, 360f,
                            centerX, centerY, 0f, true)
                    rotation.duration = 500
                    rotation.fillAfter = false
                    cardIV.startAnimation(rotation)
                    rotation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            fadeIn(contentTV)
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    /**
     * 淡入
     */
    fun fadeIn(view: View) {
        val alphaAnim = AlphaAnimation(0f, 1f)
        alphaAnim.duration = 200
        alphaAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        view.animation = alphaAnim
    }

    /**
     * 对所有View都可执行的放大动画
     */
    fun setViewZoomIn(v: View) {
        val animationSet = AnimationSet(true)
        val animation = ScaleAnimation(1.0f, 1.12f, 1.0f, 1.115f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 350 //动画效果时间
        animation.fillAfter = true
        animationSet.addAnimation(animation)
        animationSet.fillAfter = true
        v.clearAnimation()
        v.startAnimation(animationSet)
    }

    /**
     * 对所有View都可执行的缩小动画
     */
    fun setViewZoomOut(v: View) {
        val animationSet = AnimationSet(true)
        val animation = ScaleAnimation(1.12f, 1.0f, 1.115f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 350 //动画效果时间
        animation.fillAfter = true
        animationSet.addAnimation(animation)
        animationSet.fillAfter = true
        v.clearAnimation()
        v.startAnimation(animationSet)
    }
}