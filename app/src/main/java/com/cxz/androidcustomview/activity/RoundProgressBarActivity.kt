package com.cxz.androidcustomview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.dialog.LinearLoadingDialog
import com.cxz.androidcustomview.dialog.RoundLoadingDialog
import kotlinx.android.synthetic.main.activity_round_progress_bar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 自定义圆形加载进度框
 */
class RoundProgressBarActivity : AppCompatActivity() {

    private var progressNum: Int = 0

    // 圆形加载进度框
    private val roundLoadingDialog: RoundLoadingDialog by lazy {
        RoundLoadingDialog()
    }

    // 线性加载进度框
    private val linearLoadingDialog: LinearLoadingDialog by lazy {
        LinearLoadingDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress_bar)

        button.setOnClickListener {
            progressNum = 0
            roundLoadingDialog.showDialog(supportFragmentManager)
            updateRoundProgress()
        }

        button2.setOnClickListener {
            progressNum = 0
            linearLoadingDialog.showDialog(supportFragmentManager)
            updateLinearProgress()
        }

    }

    private fun updateRoundProgress() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(100)
            }
            if (progressNum == 100) {
                roundLoadingDialog.dismissDialog()
            } else {
                progressNum++
                roundLoadingDialog.setProgress(progressNum)
                updateRoundProgress()
            }
        }
    }

    private fun updateLinearProgress() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                Thread.sleep(100)
            }
            if (progressNum == 100) {
                linearLoadingDialog.dismissDialog()
            } else {
                progressNum++
                linearLoadingDialog.setProgress(progressNum)
                linearLoadingDialog.setSecondaryProgress(progressNum + 20)
                updateLinearProgress()
            }
        }
    }

}