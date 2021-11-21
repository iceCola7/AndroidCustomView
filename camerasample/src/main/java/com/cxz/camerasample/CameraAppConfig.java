package com.cxz.camerasample;

import android.app.Application;

public class CameraAppConfig {

    private static Application application;

    public static Application getInstance() {
        return application;
    }

    public static void init(Application application) {
        CameraAppConfig.application = application;
    }

}
