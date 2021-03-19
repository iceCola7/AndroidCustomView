package com.cxz.androidcustomview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
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
    private val mPaint: Paint = Paint()

    private val mTextPaint = Paint()

    /**
     * 圆环的颜色
     */
    private var circleColor: Int

    /**
     * 圆环进度的颜色
     */
    private var circleProgressColor: Int

    /**
     * 圆环进度的渐变颜色
     */
    private var circleProgressColors = mutableListOf<Int>()

    /**
     * 中间进度百分比的字符串的颜色
     */
    private var textColor: Int

    /**
     * 中间进度百分比的字符串的字体
     */
    private var textSize: Float

    /**
     * 圆环的宽度
     */
    private var roundWidth: Float

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
     * 两个圆环的间隙
     */
    private var circleGap = 0F

    /**
     * 是否是顺时针方向绘制
     */
    private var isClockWise = true

    /**
     * 是否显示圆环的百分比，true: 显示百分比  false: 只显示数字，没有百分号
     */
    private var isCirclePercent = true

    /**
     * 进度的风格，实心或者空心
     */
    private val style: Int

    companion object {
        const val STROKE = 0
        const val FILL = 1
    }

    /**
     * 动画是否可用
     */
    private var enableAnimation: Boolean = false

    /**
     * 进度
     */
    private var sweepAnglePre = 0F

    /**
     * 百分比
     */
    private var percentPre = 0

    /**
     * 进度条动画
     */
    private var animation = object : Animation() {

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            sweepAnglePre = 360 * progress / max.toFloat() * interpolatedTime
            percentPre = (100 * progress / max.toFloat() * interpolatedTime).toInt()
            postInvalidate()
            if (interpolatedTime == 1F) {
                enableAnimation = false
            }
        }
    }

    init {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar)
        //获取自定义属性和默认值
        circleColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED)
        circleProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN)
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN)
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 16F)
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 6F)
        circleGap = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundGap, 0F)
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100)
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true)
        isClockWise = mTypedArray.getBoolean(R.styleable.RoundProgressBar_roundClockWise, true)
        isCirclePercent = mTypedArray.getBoolean(R.styleable.RoundProgressBar_roundIsPercent, true)
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0)
        mTypedArray.recycle()

        // 初始化画笔
        // 消除锯齿
        mPaint.isAntiAlias = true

        // 初始化文字画笔
        mTextPaint.strokeWidth = 0F
        // 设置字体
        mTextPaint.typeface = Typeface.DEFAULT_BOLD
        mTextPaint.style = Paint.Style.FILL
        // 消除锯齿
        mTextPaint.isAntiAlias = true

        // 设置动画时长
        animation.duration = 2000L
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 画最外层的大圆环
         */
        val bigRoundWidth = roundWidth + 2 * circleGap
        // 获取圆心的x坐标
        val centerX = width / 2
        // 圆环的半径
        val bigRadius = (centerX - bigRoundWidth / 2).toInt()
        // 设置圆环的颜色
        mPaint.color = circleColor
        // 设置空心
        mPaint.style = Paint.Style.STROKE
        // 设置圆环的宽度
        mPaint.strokeWidth = bigRoundWidth
        mPaint.shader = null
        // 画出圆环
        canvas.drawCircle(centerX.toFloat(), centerX.toFloat(), bigRadius.toFloat(), mPaint)

        /**
         * 画进度百分比
         */
        mTextPaint.color = textColor
        mTextPaint.textSize = textSize
        // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
        val percent = if (enableAnimation) {
            percentPre
        } else {
            (100 * progress / max.toFloat()).toInt()
        }
        val percentStr = if (isCirclePercent) {
            "$percent%"
        } else {
            "$percent"
        }
        // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        val textWidth = mTextPaint.measureText(percentStr)
        if (textIsDisplayable && percent != 0 && style == STROKE) {
            // 画出进度百分比
            canvas.drawText(percentStr, centerX - textWidth / 2, centerX + textSize / 2, mTextPaint)
        }

        /**
         * 画圆弧 ，画圆环的进度
         */
        // 圆环的半径
        val radius = (centerX - roundWidth / 2).toInt()
        // 设置圆环的宽度
        mPaint.strokeWidth = roundWidth
        //设置进度的颜色
        mPaint.color = circleProgressColor
        if (circleProgressColors.isNotEmpty()) {
            // 绘制圆环颜色的渐变效果
            // val colors = intArrayOf(Color.GREEN, Color.YELLOW, Color.RED)
            val shader: Shader =
                    SweepGradient(centerX.toFloat(), centerX.toFloat(), circleProgressColors.toIntArray(), null)
            mPaint.shader = shader
        }
        mPaint.strokeCap = Paint.Cap.ROUND
        // 用于定义的圆弧的形状和大小的界限
        val oval = RectF(
                (centerX - radius).toFloat() + circleGap,
                (centerX - radius).toFloat() + circleGap,
                (centerX + radius).toFloat() - circleGap,
                (centerX + radius).toFloat() - circleGap
        )
        when (style) {
            STROKE -> {
                mPaint.style = Paint.Style.STROKE
            }
            FILL -> {
                mPaint.style = Paint.Style.FILL_AND_STROKE
            }
        }
        // 根据进度画圆弧
        val sweepAngle = if (enableAnimation) {
            sweepAnglePre
        } else {
            360 * progress / max.toFloat()
        }
        if (isClockWise) {
            // 顺时针
            canvas.drawArc(oval, -90f, sweepAngle, false, mPaint)
        } else {
            // 逆时针
            canvas.drawArc(oval, -90f, -1 * sweepAngle, false, mPaint)
        }
    }

    /**
     * 开启圆环动画
     */
    fun startProgressAnim(time: Long = 2000L) {
        enableAnimation = true
        animation.duration = time
        this.startAnimation(animation)
    }

    /**
     * 设置圆环的渐变颜色
     * @param colors IntArray
     */
    fun setRoundProgressColors(colors: MutableList<Int>) {
        circleProgressColors = colors
    }

    /**
     * 设置圆环的颜色
     * @param color Int
     */
    fun setRoundProgressColor(color: Int) {
        circleProgressColor = color
    }

    /**
     * 设置圆环背景颜色
     * @param color Int
     */
    fun setProgressColor(color: Int) {
        circleColor = color
    }

    /**
     * 设置字体颜色
     * @param color Int
     */
    fun setTextColor(color: Int) {
        textColor = color
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
     * @param progress
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