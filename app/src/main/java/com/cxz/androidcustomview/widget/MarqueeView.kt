package com.cxz.androidcustomview.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.cxz.androidcustomview.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * 跑马灯的view
 */
class MarqueeView : HorizontalScrollView {

    //跑马灯滚动部分
    private lateinit var mainLayout: LinearLayout
    private var mScrollSpeed = 5 //滚动速度

    private var mScrollDirection = LEFT_TO_RIGHT //滚动方向

    //当前x坐标 上一次坐标
    private var currentX = 0
    private var lastCurrentX = 0

    //View间距
    private var mViewMargin = 20

    //View总宽度
    private var viewWidth = 0

    //屏幕宽度
    private var screenWidth = 0

    private var mContext: Context
    private var scrollTime: Job? = null
    private var isStop = false
    private lateinit var mRedEnvelopeGuideView: View

    companion object {
        const val LEFT_TO_RIGHT = 1
        const val RIGHT_TO_LEFT = 2
    }

    private var mOnStopListener: (() -> Unit)? = null

    fun setOnStopListener(onStopListener: () -> Unit) {
        this.mOnStopListener = onStopListener
    }

    constructor(context: Context) : super(context, null) {
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        this.mContext = context
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.mContext = context
        initView(context)
    }

    private fun initView(context: Context) {
        val metrics = resources.displayMetrics
        screenWidth = metrics.widthPixels
        mainLayout = LayoutInflater.from(context).inflate(R.layout.marquee_scroll_content, null) as LinearLayout
        this.addView(mainLayout)
    }

    /**
     * 设置view
     * @param view View
     */
    fun addViewInQueue(view: View, redEnvelopeGuideView: View) {
        this.mRedEnvelopeGuideView = redEnvelopeGuideView
        viewWidth = 0
        mainLayout.removeAllViews()
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(mViewMargin, 0, 0, 0)
        view.layoutParams = lp
        mainLayout.addView(view)
        mainLayout.alpha = 0f
        view.measure(0, 0) //测量view
        viewWidth += view.measuredWidth + mViewMargin
    }

    //开始滚动
    fun startScroll() {
        isStop = false
        startScrollDown()
        currentX = if (mScrollDirection == LEFT_TO_RIGHT) viewWidth else -screenWidth
    }


    //设置View间距
    fun setViewMargin(viewMargin: Int) {
        this.mViewMargin = viewMargin
    }

    //设置滚动速度
    fun setScrollSpeed(scrollSpeed: Int) {
        this.mScrollSpeed = scrollSpeed
    }

    //设置滚动方向 默认从左向右
    fun setScrollDirection(scrollDirection: Int) {
        this.mScrollDirection = scrollDirection
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private fun startScrollDown() {
        scrollTime = MainScope().launch {
            mainLayout.alpha = 1f
            mRedEnvelopeGuideView.alpha = 0f
            mRedEnvelopeGuideView.visibility = View.GONE
            flow {
                while (isActive && !isStop) {
                    if (currentX != lastCurrentX) {
                        mainLayout.scrollTo(currentX, 0)
                        lastCurrentX = currentX
                        currentX++
                        delay((50 / mScrollSpeed).toLong())
                        emit(0)
                    }
                }
            }.flowOn(Dispatchers.IO)
                .collect {
                    if (currentX >= viewWidth) {
                        isStop = true
                        stopScroll()
                    }
                }
        }
    }

    //停止滚动
    private fun stopScroll() {
        if (scrollTime != null && scrollTime?.isActive == true) {
            scrollTime?.cancel()
            mOnStopListener?.invoke()
        }
    }
}