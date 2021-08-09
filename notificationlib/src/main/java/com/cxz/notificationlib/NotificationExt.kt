package com.cxz.notificationlib

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author chenxz
 * @date 2020/7/20
 * @description
 */

private var mNotificationManager: NotificationManager? = null
private var mNotificationId = 9999
private var notificationRequestCode = 1221
private val mChannelCacheMap = HashMap<String, String>()

fun Context.getNotificationManger(): NotificationManager? {
    if (mNotificationManager == null) {
        mNotificationManager =
            this.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    return mNotificationManager
}

/**
 * 展示通知栏
 * @receiver Context
 * @param title String 通知栏标题
 * @param subTitle String 通知栏副标题
 * @param content String 通知栏内容
 * @param intent Intent? 点击通知栏跳转的意图
 * @param notificationId Int 通知栏的Id，相同的Id可以刷新通知栏
 * @param channelId String 通知栏渠道Id
 * @param channelName String 通知栏渠道名称
 */
fun Context.showNotification(
    title: String,
    subTitle: String = "",
    content: String = "",
    intent: Intent? = null,
    notificationId: Int = mNotificationId,
    channelId: String = this.packageName,
    channelName: String = this.getString(R.string.app_name)
) {
    val notification = buildNotification(title, subTitle, content, intent, channelId, channelName)
    getNotificationManger()?.notify(notificationId, notification)
    mNotificationId += 1
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
 * @param subTitle String 通知栏副标题
 * @param content String 通知栏内容
 * @param intent Intent? 点击通知栏跳转的意图
 * @param channelId String 通知栏渠道Id
 * @param channelName String 通知栏渠道名称
 * @return Notification
 */
fun Context.buildNotification(
    title: String,
    subTitle: String = "",
    content: String = "",
    intent: Intent? = null,
    channelId: String = this.packageName,
    channelName: String = this.getString(R.string.app_name)
): Notification {
    var notification: Notification? = null
    var builder: NotificationCompat.Builder? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (!mChannelCacheMap.containsKey(channelId)) {
            // 创建 NotificationChannel
            createNotificationChannel(channelId, channelName)
            mChannelCacheMap[channelId] = channelName
        }
        builder = NotificationCompat.Builder(this.applicationContext, channelId)
    } else {
        builder = NotificationCompat.Builder(this.applicationContext)
    }
    if (intent != null) {
        val pendingIntent =
            PendingIntent.getActivity(
                this, notificationRequestCode,
                intent, PendingIntent.FLAG_ONE_SHOT
            )
        builder.setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
    }
    builder.setSmallIcon(R.mipmap.ic_launcher) // 设置小图标  只能使用alpha图层的图片进行设置
        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
        .setContentTitle(title) // 设置标题
        .setSubText(subTitle) // 设置副标题
        .setContentText(content) // 设置内容
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 设置通知的重要程度
        .setWhen(System.currentTimeMillis()) // 设置时间
        .setAutoCancel(true) // 设置为自动取消
        .setDefaults(NotificationCompat.DEFAULT_ALL) // 使用默认效果， 会根据手机当前环境播放铃声， 是否振动
    notification = builder.build()
    return notification
}

/**
 * 创建 NotificationChannel
 * @param channelId String 通知栏渠道Id
 * @param channelName String 通知栏渠道名称
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun Context.createNotificationChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            // 通知栏出现闪灯
            enableLights(true)
            // 小圆点颜色
            lightColor = Color.RED
            // 设置是否震动
            enableVibration(true)
            // 是否在久按桌面图标时显示此渠道的通知
            setShowBadge(true)
            // 是否可绕过请勿打扰模式
            setBypassDnd(true)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
        }
        getNotificationManger()?.createNotificationChannel(channel)
    }
}

/**
 * 创建 NotificationChannelGroup
 * @param groupId String
 * @param groupName String
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun Context.createNotificationChannelGroup(groupId: String, groupName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelGroup = NotificationChannelGroup(groupId, groupName)
        getNotificationManger()?.createNotificationChannelGroup(channelGroup)
    }
}


/**
 * 打开通知
 */
fun Context.openNotificationChannel(channelId: String = this.packageName): Boolean {
    // 判断通知是否打开
    if (!isNotificationEnabled()) {
        toNotificationSetting(channelId)
        return false
    }
    // 判断渠道是否打开
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = getNotificationManger()?.getNotificationChannel(channelId)
        if (channel?.importance == NotificationManager.IMPORTANCE_NONE) {
            toNotificationSetting(channel.id)
            return false
        }
    }
    return true
}

private const val CHECK_OP_NO_THROW = "checkOpNoThrow"
private const val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"

/**
 * 判断通知是否打开
 */
fun Context.isNotificationEnabled(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        return notificationManagerCompat.areNotificationsEnabled()
    }
    var appOpsClass: Class<*>? = null
    try {
        appOpsClass = Class.forName(AppOpsManager::class.java.name)
        val checkOpNoThrowMethod =
            appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.javaClass)
        val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
        val value = opPostNotificationValue[Int::class.java] as Int
        return checkOpNoThrowMethod.invoke(
            this.getSystemService(Context.APP_OPS_SERVICE),
            value,
            this.applicationInfo.uid,
            this.packageName
        ) as Int == AppOpsManager.MODE_ALLOWED
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/**
 * 手动打开应用通知
 */
fun Context.toNotificationSetting(channelId: String = this.packageName) {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // 适配8.0及8.0以上（8.0需要打开应用通知，再打开渠道通知）
        if (channelId.isEmpty()) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
        } else {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // 适配5.0及5.0以上
        intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra("app_package", this.packageName)
        intent.putExtra("app_uid", this.applicationInfo.uid)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 适配4.4及4.4以上
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.fromParts("package", this.packageName, null)
    } else {
        intent.action = Settings.ACTION_SETTINGS
    }
    this.startActivity(intent)
}

/**
 * 判断进程是否是后台
 * @return true: 后台  false: 前台
 */
fun Context.isProcessBackground(): Boolean {
    val activityManager: ActivityManager =
        this.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses: List<ActivityManager.RunningAppProcessInfo> =
        activityManager.runningAppProcesses
    for (appProcess in appProcesses) {
        if (appProcess.processName == this.packageName) {
            return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
    }
    return false
}
