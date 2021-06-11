package com.cxz.behaviorsample.ext

import android.content.Context
import android.util.TypedValue

/**
 * @author chenxz
 * @date 2020/9/12
 * @desc
 */

fun Context.getStatusBarHeight(): Int {
    val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    return this.resources.getDimensionPixelSize(resourceId)
}

fun Context.dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics).toInt()
}
