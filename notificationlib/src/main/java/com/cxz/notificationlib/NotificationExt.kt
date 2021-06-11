package com.cxz.notificationlib

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author chenxz
 * @date 2020/7/20
 * @description
 */

private var mNotificationManager: NotificationManager? = null
private var isCreateChannel = false
private var mNotificationId = 9999
private var notificationRequestCode = 1221

/**
 * 展示通知栏
 * @receiver Context
 * @param title String 通知栏标题
 * @param content String 通知栏内容
 * @param intent Intent? 点击通知栏跳转的意图
 * @param notificationId Int 通知栏的Id，相同的Id可以刷新通知栏
 */
fun Context.showNotification(
    title: String,
    content: String,
    intent: Intent? = null,
    notificationId: Int = mNotificationId
) {
    val notification = buildNotification(title, content, intent)
    mNotificationManager?.notify(notificationId, notification)
}

/**
 * 构建 ARouter 跳转的 Intent
 * @receiver Context
 * @param aRouterPath String ARouter 路由地址
 * @param bundle Bundle? 参数
 * @return Intent
 */
fun Context.buildARouterIntent(aRouterPath: String, bundle: Bundle? = null): Intent {
    val postcard = ARouter.getInstance().build(aRouterPath)
    LogisticsCenter.completion(postcard)
    val destination = postcard.destination
    val intent = Intent(this, destination)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    return intent
}

/**
 * 构建通知栏
 * @receiver Context
 * @param title String 通知栏标题
 * @param content String 通知栏内容
 * @param intent Intent? 点击通知栏跳转的意图
 * @return Notification
 */
fun Context.buildNotification(
    title: String,
    content: String,
    intent: Intent? = null
): Notification {
    if (mNotificationManager == null) {
        mNotificationManager =
            this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    var notification: Notification? = null
    var builder: NotificationCompat.Builder? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = this.packageName
        val channelName = this.getString(R.string.app_name)
        if (!isCreateChannel) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            // 是否在桌面上icon右上角显示小圆点
            channel.enableLights(true)
            // 小圆点颜色
            channel.lightColor = Color.RED
            // 设置是否震动
            channel.enableVibration(true)
            // 是否在久按桌面图标时显示此渠道的通知
            channel.setShowBadge(true)
            mNotificationManager?.createNotificationChannel(channel)
            isCreateChannel = true
        }
        builder = NotificationCompat.Builder(this.applicationContext, channelId)
    } else {
        builder = NotificationCompat.Builder(this.applicationContext)
    }
    if (intent != null) {
        val pendingIntent =
            PendingIntent.getActivity(this, notificationRequestCode, intent, PendingIntent.FLAG_ONE_SHOT)
        builder.setContentIntent(pendingIntent)
    }
    builder.setSmallIcon(R.mipmap.ic_launcher) // 设置小图标  只能使用alpha图层的图片进行设置
        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
        .setContentTitle(title) // 设置标题
        .setContentText(content) // 设置内容
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 设置通知的重要程度
        .setWhen(System.currentTimeMillis()) // 设置时间
        .setAutoCancel(true) // 设置为自动取消
        .setDefaults(NotificationCompat.DEFAULT_ALL) // 使用默认效果， 会根据手机当前环境播放铃声， 是否振动
    notification = builder.build()
    return notification
}

/**
 * 判断进程是否是后台
 * @return true: 后台  false: 前台
 */
fun Context.isProcessBackground(): Boolean {
    val activityManager: ActivityManager =
        this.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses: List<ActivityManager.RunningAppProcessInfo> = activityManager.runningAppProcesses
    for (appProcess in appProcesses) {
        if (appProcess.processName == this.packageName) {
            return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
    }
    return false
}
