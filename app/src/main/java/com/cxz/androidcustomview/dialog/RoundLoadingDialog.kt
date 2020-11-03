package com.cxz.androidcustomview.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cxz.androidcustomview.R
import kotlinx.android.synthetic.main.dialog_round_loading.*

/**
 * @author chenxz
 * @date 2020/10/28
 * @desc 圆形加载进度框
 */
class RoundLoadingDialog : DialogFragment {

    private var loadingText: String = ""

    private var isShowError: Boolean = false

    constructor(loadingText: String = "准备中...") {
        this.loadingText = loadingText
    }

    // 重试的回调
    private var onRetryClick: (() -> Unit)? = null

    fun setRetryClick(onRetryClick: (() -> Unit)) {
        this.onRetryClick = onRetryClick
    }

    override fun onStart() {
        super.onStart()
        val dialogWidth = dp2px(285f)
        dialog?.window?.apply {
            setLayout(dialogWidth, WindowManager.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.CENTER)
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_round_loading, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        loadingTV?.text = loadingText

        if (isShowError) {
            downloadLL.visibility = View.GONE
            downloadErrorLL.visibility = View.VISIBLE
        } else {
            downloadLL.visibility = View.VISIBLE
            downloadErrorLL.visibility = View.GONE
        }

        retryTV?.highlightColor = Color.TRANSPARENT
        val retryStr = "加载失败重新加载"
        val spannableStringBuilder = SpannableStringBuilder(retryStr)
        spannableStringBuilder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                setLoadError(false)
                dismissDialog()
                onRetryClick?.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }, 4, retryStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF8219")),
            4,
            retryStr.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        retryTV?.text = spannableStringBuilder
        retryTV?.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * 显示Dialog
     */
    fun showDialog(fragmentManager: FragmentManager) {
        super.show(fragmentManager, "round_dialog")
    }

    /**
     * 隐藏Dialog
     */
    fun dismissDialog() {
        if (this.dialog?.isShowing == true) {
            dismiss()
        }
    }

    /**
     * 是否显示加载失败的页面
     * @param isShowError Boolean
     */
    fun setLoadError(isShowError: Boolean) {
        this.isShowError = isShowError
        if (isShowError) {
            downloadLL.visibility = View.GONE
            downloadErrorLL.visibility = View.VISIBLE
        } else {
            downloadLL.visibility = View.VISIBLE
            downloadErrorLL.visibility = View.GONE
        }
    }

    /**
     * 更新进度
     * @param progress Int
     */
    fun setProgress(progress: Int) {
        roundProgressBar?.setProgress(progress)
    }

    /**
     * 设置加载文字
     * @param text String
     */
    fun setLoadingText(text: String) {
        this.loadingText = text
        loadingTV?.text = text
    }

    private fun dp2px(dpVal: Float): Int {
        return (TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics
        ) + 0.5).toInt()
    }

}