package com.bo.anim.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by TT on 2017-12-07.
 */

public class PermissionUtil {


    //检查是否添加权限
    public static boolean checkPermission(String permission,Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.e("checkPermission", "PERMISSION_GRANTED" + ContextCompat.checkSelfPermission(context, permission));
                return true;
            } else {
                Log.e("checkPermission", "PERMISSION_DENIED" + ContextCompat.checkSelfPermission(context, permission));
                return false;
            }
        } else {
            Log.e("checkPermission", "M以下" + ContextCompat.checkSelfPermission(context, permission));
            return true;
        }
    }


    public static void startRequestPermision( String permission,Activity context) {
        String []string_array = {permission};
        ActivityCompat.requestPermissions(context, string_array, 1);
    }
}
