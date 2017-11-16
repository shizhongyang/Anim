package com.bo.anim.ui;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by TT on 2017-10-12.
 */

public class MyApplication extends Application {

    public static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        instance = this;
        // Normal app init code...

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName()
    {
        try
        {
            PackageManager packageManager = instance.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    instance.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return instance.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
