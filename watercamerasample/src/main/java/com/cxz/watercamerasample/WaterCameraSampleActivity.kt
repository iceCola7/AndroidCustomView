package com.cxz.watercamerasample

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_water_camera_sample.*

class WaterCameraSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_camera_sample)

        button.setOnClickListener {
            val rxPermissions = RxPermissions(this).request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe {
                if (it) {
                    startActivity(
                        Intent(
                            this@WaterCameraSampleActivity,
                            WaterCameraActivity::class.java
                        )
                    )
                }
            }
        }
    }
}
