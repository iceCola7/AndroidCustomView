package com.cxz.androidcustomview.util;

import android.util.Log;

/**
 * Created by chenxz on 2018/8/3.
 */
public class XLog {

    public static final String TAG = "CXZ";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

}
