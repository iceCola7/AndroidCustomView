package com.cxz.behaviorsample.weiget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author chenxz
 * @date 2020/9/11
 * @description
 */
class DrawableLeftTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        val drawables = compoundDrawables
        val drawableLeft = drawables[0]
        if (drawableLeft != null) {
            val textWidth = paint.measureText(text.toString())
            val drawablePadding = compoundDrawablePadding
            val drawableWidth =  drawableLeft.intrinsicWidth
            val bodyWidth = textWidth + drawableWidth + drawablePadding
            canvas!!.translate((width - bodyWidth) / 2, 0f)
        }
        super.onDraw(canvas)
    }

}