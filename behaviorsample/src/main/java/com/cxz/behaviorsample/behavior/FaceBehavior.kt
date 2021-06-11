package com.cxz.behaviorsample.behavior

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.palette.graphics.Palette
import com.cxz.behaviorsample.R
import com.cxz.behaviorsample.ext.getStatusBarHeight
import kotlin.math.roundToInt

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc Face 部分的 Behavior
 */
class FaceBehavior : CoordinatorLayout.Behavior<View> {

    // topBar 高度
    private var topBarHeight = 0

    // 滑动内容初始化 TransY
    private var contentTransY = 0F

    // 下滑时的终点值
    private var downEndY = 0F

    // 图片往上位移值
    private var faceTransY = 0f

    // 蒙层的背景
    private var drawable: GradientDrawable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        topBarHeight = context.resources.getDimension(R.dimen.top_bar_height).toInt() + context.getStatusBarHeight()
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
        downEndY = context.resources.getDimension(R.dimen.content_trans_down_end_y)
        faceTransY = context.resources.getDimension(R.dimen.face_trans_y)

        // 抽取图片资源的亮色或者暗色作为蒙层的背景渐变色
        val palette: Palette = Palette.from(BitmapFactory.decodeResource(context.resources, R.mipmap.jj)).generate()
        val vibrantSwatch: Palette.Swatch? = palette.vibrantSwatch
        val mutedSwatch: Palette.Swatch? = palette.mutedSwatch
        val colors = IntArray(2)
        if (mutedSwatch != null) {
            colors[0] = mutedSwatch.rgb
            colors[1] = getTranslucentColor(0.6f, mutedSwatch.rgb)
        } else if (vibrantSwatch != null) {
            colors[0] = vibrantSwatch.rgb
            colors[1] = getTranslucentColor(0.6f, vibrantSwatch.rgb)
        } else {
            colors[0] = Color.parseColor("#4D000000")
            colors[1] = getTranslucentColor(0.6f, Color.parseColor("#4D000000"))
        }
        drawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        // 依赖 Content View
        return dependency.id == R.id.ll_content
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        // 设置蒙层背景
        child.findViewById<View>(R.id.v_mask).background = drawable
        return false
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        // 计算Content的上滑百分比、下滑百分比
        val upPro: Float = (contentTransY - MathUtils.clamp(
            dependency.translationY,
            topBarHeight.toFloat(),
            contentTransY
        )) / (contentTransY - topBarHeight)
        val downPro: Float = (downEndY - MathUtils.clamp(
            dependency.translationY,
            contentTransY,
            downEndY
        )) / (downEndY - contentTransY)

        val imageView = child.findViewById<ImageView>(R.id.iv_face)
        val maskView = child.findViewById<View>(R.id.v_mask)
        if (dependency.translationY >= contentTransY) {
            // 根据Content上滑百分比位移图片TransitionY
            imageView.translationY = downPro * faceTransY
        } else {
            // 根据Content下滑百分比位移图片TransitionY
            imageView.translationY = faceTransY + 4 * upPro * faceTransY
        }
        //根据Content上滑百分比设置图片和蒙层的透明度
        imageView.alpha = 1 - upPro
        maskView.alpha = upPro
        //因为改变了child的位置，所以返回true
        return true
    }

    /**
     * 获取沉浸式透明颜色
     * @param percent Float
     * @param rgb Int
     * @return Int
     */
    private fun getTranslucentColor(percent: Float, rgb: Int): Int {
        val blue = Color.blue(rgb)
        val green = Color.green(rgb)
        val red = Color.red(rgb)
        var alpha = Color.alpha(rgb)
        alpha = (alpha * percent).roundToInt()
        return Color.argb(alpha, red, green, blue)
    }

}