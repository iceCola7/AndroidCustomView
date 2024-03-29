package com.cxz.camerasample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cxz.camerasample.glsurfaceview.GLSurfaceCamera2Activity;
import com.cxz.camerasample.glsurfaceview.GLSurfaceCameraActivity;
import com.cxz.camerasample.surfaceview.SurfaceCamera2Activity;
import com.cxz.camerasample.surfaceview.SurfaceCameraActivity;
import com.cxz.camerasample.textureview.TextureCamera2Activity;
import com.cxz.camerasample.textureview.TextureCameraActivity;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        checkPermission();
    }

    public void startCameraActivity(View view) {
        Intent intent = null;
        int id = view.getId();
        if (id == R.id.camera_btn1) {
            intent = new Intent(this, SurfaceCameraActivity.class);
        } else if (id == R.id.camera_btn2) {
            intent = new Intent(this, TextureCameraActivity.class);
        } else if (id == R.id.camera_btn3) {
            intent = new Intent(this, GLSurfaceCameraActivity.class);
        } else if (id == R.id.camera_btn4) {
            intent = new Intent(this, SurfaceCamera2Activity.class);
        } else if (id == R.id.camera_btn5) {
            intent = new Intent(this, TextureCamera2Activity.class);
        } else if (id == R.id.camera_btn6) {
            intent = new Intent(this, GLSurfaceCamera2Activity.class);
        }
        startActivity(intent);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请在设置中打开摄像头和存储权限", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 200);
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200) {
            checkPermission();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
