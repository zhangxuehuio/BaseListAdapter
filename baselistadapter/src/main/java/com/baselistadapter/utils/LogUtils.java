package com.baselistadapter.utils;

import android.util.Log;

import com.baselistadapter.BuildConfig;


/**
 * Created by zhangxuehui on 2018/6/14.
 */

public class LogUtils {
    public static final void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e("BaseListAdapter", msg);
        }
    }

    public static final void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static final void e(Object obj, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(obj.getClass().getName(), msg);
        }
    }
}
