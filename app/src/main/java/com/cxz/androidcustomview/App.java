package com.cxz.androidcustomview;

import android.app.Application;

import com.cxz.camerasample.CameraAppConfig;

public class App  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CameraAppConfig.init(this);
    }
}
