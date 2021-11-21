package com.cxz.watercamerasample

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxz.watercamerasample.CameraUtil
import com.cxz.watercamerasample.camera.CameraProxy
import com.cxz.watercamerasample.sufaceview.CameraSurfaceView
import kotlinx.android.synthetic.main.activity_water_camera.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


class WaterCameraActivity : AppCompatActivity() {

    private val TAG = "WaterCameraActivity"

    private var mCameraView: CameraSurfaceView? = null
    private var mCameraProxy: CameraProxy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // 全屏
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_camera)

        initCamera()

        iv_take_photo.setOnClickListener {
            mCameraProxy?.takePicture { data, camera ->
                Thread {
                    val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    val rotation = mCameraProxy?.latestRotation ?: 90
                    val rotateBitmap = CameraUtil.rotateBitmap(
                        bitmap,
                        rotation,
                        mCameraProxy?.isFrontCamera ?: false,
                        true
                    )
                    val path = CameraUtil.getPath()
                    CameraUtil.saveBitmap(rotateBitmap, path, this)
                }.start()
            }
        }
        toolbar_close_iv.setOnClickListener {
            finish()
        }

    }

    private fun initCamera() {
        mCameraView = CameraSurfaceView(this)
        mCameraProxy = mCameraView?.cameraProxy
        camera_preview.addView(mCameraView)
    }

    private fun compressImage(file: File, path: String) {
        Luban.with(this@WaterCameraActivity)
            .load(file)     // 传人要压缩的图片列表
            .ignoreBy(200)   // 忽略不压缩图片的大小
            .setTargetDir(path)  // 设置压缩后文件存储位置
            .setCompressListener(object : OnCompressListener { //设置回调
                override fun onStart() {}
                override fun onSuccess(file: File?) {
                    // 刷新系统相册，刷新系统相册的操作实际上就是发送了系统内置的广播
                    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    val uri = Uri.fromFile(File(path))
                    intent.data = uri
                    sendBroadcast(intent)
                }

                override fun onError(e: Throwable) {}
            }).launch()    //启动压缩
    }
}
