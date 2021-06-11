package com.cxz.androidcustomview.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxz.androidcustomview.R
import com.cxz.notificationlib.showNotification
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        button.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            showNotification("测试通知栏标题", "测试通知栏内容", intent)
        }

    }
}