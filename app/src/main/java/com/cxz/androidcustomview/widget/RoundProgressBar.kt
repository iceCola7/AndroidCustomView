package com.cxz.androidcustomview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cxz.androidcustomview.R

/**
 * @author chenxz
 * @date 2020/11/2
 * @desc 圆形的加载进度框
 */
class RoundProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    View(context, attrs, defStyle) {
    /**
     * 画笔对象的引用
     */
    private val paint: Paint = Paint()

    /**
     * 圆环的颜色
     */
    var circleColor: Int

    /**
     * 圆环进度的颜色
     */
    var circleProgressColor: Int

    /**
     * 中间进度百分比的字符串的颜色
     */
    var textColor: Int

    /**
     * 中间进度百分比的字符串的字体
     */
    var textSize: Float

    /**
     * 圆环的宽度
     */
    var roundWidth: Float

    /**
     * 最大进度
     */
    private var max: Int = 100

    /**
     * 当前进度
     */
    private var progress = 0

    /**
     * 是否显示中间的进度
     */
    private val textIsDisplayable: Boolean

    /**
     * 进度的风格，实心或者空心
     */
    private val style: Int

    companion object {
        const val STROKE = 0
        const val FILL = 1
    }

    init {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar)
        //获取自定义属性和默认值
        circleColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED)
        circleProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN)
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15f)
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5f)
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100)
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true)
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0)
        mTypedArray.recycle()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 画最外层的大圆环
         */
        // 获取圆心的x坐标
        val centre = width / 2
        // 圆环的半径
        val radius = (centre - roundWidth / 2).toInt()
        // 设置圆环的颜色
        paint.color = circleColor
        // 设置空心
        paint.style = Paint.Style.STROKE
        // 设置圆环的宽度
        paint.strokeWidth = roundWidth
        // 消除锯齿
        paint.isAntiAlias = true
        // 画出圆环
        canvas.drawCircle(centre.toFloat(), centre.toFloat(), radius.toFloat(), paint)
        /**
         * 画进度百分比
         */
        paint.strokeWidth = 0f
        paint.color = textColor
        paint.textSize = textSize
        // 设置字体
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.style = Paint.Style.FILL
        // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
        val percent = (progress.toFloat() / max.toFloat() * 100).toInt()
        // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        val textWidth = paint.measureText("$percent%")
        if (textIsDisplayable && percent != 0 && style == STROKE) {
            // 画出进度百分比
            canvas.drawText("$percent%", centre - textWidth / 2, centre + textSize / 2, paint)
        }
        /**
         * 画圆弧 ，画圆环的进度
         */
        // 设置进度是实心还是空心
        // 设置圆环的宽度
        paint.strokeWidth = roundWidth
        //设置进度的颜色
        paint.color = circleProgressColor
        // 用于定义的圆弧的形状和大小的界限
        val oval = RectF(
            (centre - radius).toFloat(),
            (centre - radius).toFloat(),
            (centre + radius).toFloat(),
            (centre + radius).toFloat()
        )
        when (style) {
            STROKE -> {
                paint.style = Paint.Style.STROKE
                // 根据进度画圆弧
                canvas.drawArc(oval, -90f, 360 * progress / max.toFloat(), false, paint)
            }
            FILL -> {
                paint.style = Paint.Style.FILL_AND_STROKE
                // 根据进度画圆弧
                if (progress != 0) canvas.drawArc(oval, -90f, 360 * progress / max.toFloat(), true, paint)
            }
        }
    }

    @Synchronized
    fun getMax(): Int {
        return max
    }

    /**
     * 设置进度的最大值
     * @param max
     */
    @Synchronized
    fun setMax(max: Int) {
        require(max >= 0) { "max not less than 0" }
        this.max = max
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    @Synchronized
    fun getProgress(): Int {
        return progress
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param progress 进度
     */
    @Synchronized
    fun setProgress(progress: Int) {
        var progress = progress
        require(progress >= 0) { "progress not less than 0" }
        if (progress > max) {
            progress = max
        }
        if (progress <= max) {
            this.progress = progress
            postInvalidate()
        }
    }
}