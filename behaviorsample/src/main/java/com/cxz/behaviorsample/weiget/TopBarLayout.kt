package com.cxz.behaviorsample.weiget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.cxz.behaviorsample.ext.getStatusBarHeight

/**
 * @author chenxz
 * @date 2020/9/11
 * @description
 */
class TopBarLayout : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val newHeightMeasureSpec =
            MeasureSpec.makeMeasureSpec(measuredHeight + context.getStatusBarHeight(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }
}