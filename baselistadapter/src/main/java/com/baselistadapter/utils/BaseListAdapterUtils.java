package com.baselistadapter.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zhangxuehui on 2018/6/23.
 */

public class BaseListAdapterUtils {
    private static Application mApp;

    public static void init(Application application) {
        if (mApp == null) {
            BaseListAdapterUtils.mApp = application;
        }
    }

    public static void init(Context context) {
        init((Application) context.getApplicationContext());
    }

    public static Application getApp() {
        if (mApp != null) return mApp;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            init((Application) app);
            return mApp;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
