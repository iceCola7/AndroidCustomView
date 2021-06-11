package com.cxz.behaviorsample.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import com.cxz.behaviorsample.R
import com.cxz.behaviorsample.ext.getStatusBarHeight

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc TopBar 部分的 Behavior
 */
class TopBarBehavior : CoordinatorLayout.Behavior<View> {

    // topBar 高度
    private var topBarHeight = 0

    // 滑动内容初始化 TransY
    private var contentTransY = 0F

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        topBarHeight = (context.resources.getDimension(R.dimen.top_bar_height) + context.getStatusBarHeight()).toInt()
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        // 依赖 Content View
        return dependency.id == R.id.ll_content
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        // 计算Content上滑的百分比，设置子view的透明度
        val upPro: Float = (contentTransY - MathUtils.clamp(
            dependency.translationY,
            topBarHeight.toFloat(),
            contentTransY
        )) / (contentTransY - topBarHeight)
        val tvName = child.findViewById<View>(R.id.tv_top_bar_name)
        val tvColl = child.findViewById<View>(R.id.tv_top_bar_coll)
        tvName.alpha = upPro
        tvColl.alpha = upPro
        return true
    }
}