package com.cxz.androidcustomview.activity

import android.content.Intent
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.base.BaseActivity
import com.cxz.notificationlib.showNotification
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_notification
    }

    override fun initView() {
        val title = intent.getStringExtra("title")
        setToolbarTitle(title)

        button.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            showNotification("测试通知栏标题", "", "测试通知栏内容", intent)
        }
    }
}