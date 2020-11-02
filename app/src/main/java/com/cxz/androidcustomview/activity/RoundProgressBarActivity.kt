package com.cxz.androidcustomview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.dialog.GameLoadingDialog
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

    private val gameLoadingDialog: GameLoadingDialog by lazy {
        GameLoadingDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_progress_bar)

        button.setOnClickListener {
            progressNum = 0
            gameLoadingDialog.showDialog(supportFragmentManager)
            updateProgress()
        }

    }

    private fun updateProgress() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(100)
            }
            if (progressNum == 100) {
                gameLoadingDialog.dismissDialog()
            } else {
                progressNum++
                gameLoadingDialog.setProgress(progressNum)
                updateProgress()
            }
        }
    }

}