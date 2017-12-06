package com.bo.anim.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by TT on 2017-12-04.
 */

public class ClsUtils {

    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    @SuppressWarnings("unchecked")
    public static boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean invoke = (Boolean) createBondMethod.invoke(btDevice);
        return invoke;
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    public static boolean remvoeBond(Class<?> btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBond = btClass.getMethod("removeBond");
        Boolean invoke = (Boolean) removeBond.invoke(btDevice);
        return invoke;
    }

    public static boolean setPin(Class<? extends BluetoothDevice> btClass,
                                 BluetoothDevice btDevice, String str) throws Exception {

        Boolean aBoolean = null;
        try {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin", new Class[]{byte[].class});

            aBoolean = (Boolean) removeBondMethod.invoke(btDevice, new Object[]{str.getBytes()});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return aBoolean;

    }

    //取消用户输入
    public static boolean cancelPairingUserInput(Class<?> btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
//        cancelBondProcess(btClass, device);
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    // 取消配对
    public static  boolean cancelBondProcess(Class<?> btClass,
                                            BluetoothDevice device)

            throws Exception {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    //确认配对

    public static  void setPairingConfirmation(Class<?> btClass, BluetoothDevice device, boolean isConfirm) throws Exception {
        Method setPairingConfirmation = btClass.getDeclaredMethod("setPairingConfirmation", boolean.class);
        setPairingConfirmation.invoke(device, isConfirm);
    }
    
    public static void printAllInform(Class clasShow){
        try {
            Method[] methods = clasShow.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.e("method name", methods[i].getName() + ";and the i is:"
                        + i);
            }

            Field[] fields = clasShow.getFields();
            for (int i = 0; i < fields.length; i++) {
                Log.e("Field name", fields[i].getName());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
}
