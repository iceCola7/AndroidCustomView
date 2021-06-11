package com.cxz.selectcity.sample.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.cxz.selectcity.sample.R

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
class SideLetterBar : View {
    private val b = arrayOf(
        "定位",
        "热门",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    )

    private var choose = -1
    private val paint = Paint()
    private var showBg = false
    private var overlay: TextView? = null

    private var onLetterChangedListener: ((String) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setOverlay(overlay: TextView?) {
        this.overlay = overlay
    }

    fun setOnLetterChangedListener(onLetterChangedListener: ((String) -> Unit)) {
        this.onLetterChangedListener = onLetterChangedListener
    }

    fun changeLetterView(letter: String) {
        for (i in b.indices) {
            if (b[i] == letter) {
                choose = i
                break
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT)
        }
        val singleHeight = height / b.size
        for (i in b.indices) {
            paint.textSize = resources.getDimension(R.dimen.font_12sp)
            if (i == 0 || i == 1) {
                paint.color = Color.parseColor("#FFD24B")
            } else {
                paint.color = Color.parseColor("#999999")
            }
            paint.isAntiAlias = true
            if (i == choose) {
                paint.color = Color.parseColor("#5c5c5c")
                //加粗
                paint.isFakeBoldText = true
            }
            val xPos = width / 2 - paint.measureText(b[i]) / 2
            val yPos = (singleHeight * i + singleHeight).toFloat()
            canvas.drawText(b[i], xPos, yPos, paint)
            paint.reset()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y
        val oldChoose = choose
        val c = (y / height * b.size).toInt()

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                showBg = true
                if (oldChoose != c && onLetterChangedListener != null) {
                    if (c >= 0 && c < b.size) {
                        onLetterChangedListener?.invoke(b[c])
                        choose = c
                        invalidate()
                        overlay?.let {
                            it.visibility = VISIBLE
                            it.text = b[c]
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (oldChoose != c && onLetterChangedListener != null) {
                    if (c >= 0 && c < b.size) {
                        onLetterChangedListener?.invoke(b[c])
                        choose = c
                        invalidate()
                        overlay?.let {
                            it.visibility = VISIBLE
                            it.text = b[c]
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                showBg = false
                choose = -1
                invalidate()
                overlay?.visibility = GONE
            }
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}