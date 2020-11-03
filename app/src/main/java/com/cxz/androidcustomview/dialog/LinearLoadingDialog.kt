package com.cxz.androidcustomview.dialog

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.cxz.androidcustomview.R
import kotlinx.android.synthetic.main.dialog_linear_loading.*

/**
 * @author chenxz
 * @date 2020/11/3
 * @desc 线性加载进度框
 */
class LinearLoadingDialog : DialogFragment {

    private var loadingText: String = ""

    private var isShowError: Boolean = false

    constructor(loadingText: String = "准备中，请稍后...") {
        this.loadingText = loadingText
    }

    // 重试的回调
    private var onRetryClick: (() -> Unit)? = null

    fun setRetryClick(onRetryClick: (() -> Unit)) {
        this.onRetryClick = onRetryClick
    }

    override fun onStart() {
        super.onStart()
        val dialogWidth = dp2px(350F) ?: WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.apply {
            decorView.setPadding(0, 0, 0, 0)
            val lp = attributes
            lp.width = dialogWidth
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.TOP
            lp.windowAnimations = R.style.TopDialogAnimation
            attributes = lp
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_linear_loading, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        loadingTV.text = loadingText
        progressTV.text = "0%"

        if (isShowError) {
            downloadLL.visibility = View.GONE
            downloadErrorLL.visibility = View.VISIBLE
        } else {
            downloadLL.visibility = View.VISIBLE
            downloadErrorLL.visibility = View.GONE
        }

        closeIV.setOnClickListener {
            dismissDialog()
        }

        retryBtn.setOnClickListener {
            setLoadError(false)
            dismissDialog()
            onRetryClick?.invoke()
        }
    }

    /**
     * 显示Dialog
     */
    fun showDialog(fragmentManager: FragmentManager) {
        super.show(fragmentManager, "linear_dialog")
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
        progressbar?.progress = progress
        progressTV?.text = "$progress%"
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