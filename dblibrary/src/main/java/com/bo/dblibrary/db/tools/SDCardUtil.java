package com.bo.dblibrary.db.tools;

import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TT on 2017-11-16.
 */

public class SDCardUtil {

    /**
     * 判断SDcard是否可用
     * Environment.MEDIA_MOUNTED 介质已加载完 Environment.MEDIA_MOUNTED_READ_ONLY
     * 介质已加载完，但是只读状态 Environment.MEDIA_CHECKING
     * 正在检测介质 Environment.MEDIA_UNKNOWN
     *  介质未知 Environment.MEDIA_UNMOUNTED 介质已卸载
     *
     */
    public static boolean isSDCardMounted() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        return flag;
    }

    /**
     * 获取Sdcard的目录
     */
    public static String getSDCardPath() {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        return path;
    }


    /**
     * 获取扩展SD卡存储目录
     *
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getExternalSdCardPath() {

        if (isSDCardMounted()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getAbsolutePath();
        }

        String path = null;

        File sdCardFile = null;

        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;
    }

    private static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/system/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 获取总大小 (字节)
     */
    public static long getSDCardSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardPath());
            long count = statFs.getBlockCount();
            long size = statFs.getBlockSize();
            return count * size;
        }
        return 0;
    }

    /**
     * 获取剩余空间大小
     */
    public static long getSDCardFreeSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardPath());
            long count = statFs.getFreeBlocks();
            long size = statFs.getBlockSize();
            return count * size;
        }
        return 0;
    }

    /**
     * 返回可用空间
     */
    public static long getSDCardAvailableSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardPath());
            long count = statFs.getAvailableBlocks();
            long size = statFs.getBlockSize();
            return count * size;
        }
        return 0;
    }

    /**
     * 保存文件到SDcard
     *
     * @param data
     *            数据
     * @param dir
     *            文件目录
     * @param filename
     *            文件名
     * @return
     */
    public static boolean saveFileToSDCard(byte[] data, String dir,
                                           String filename) {
        if (isSDCardMounted()) {
            File filedir = new File(getSDCardPath() + File.separator + dir);
            if (!filedir.exists()) {// 判断是否存在目录，不存在则创建
                filedir.mkdirs();
            }
            if (getSDCardAvailableSize() >= data.length) {// 判断是否有足够剩余空间
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream((new File(filedir, filename)),
                            true);
                    fos.write(data);
                    fos.write("#".getBytes());
                    return true;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 从Sdcard里面读取数据
     */

    public static byte[] readFileFromSDCard(String filepath) {
        if (isSDCardMounted()) {
            File file = new File(filepath);
            ByteArrayOutputStream bos = null;
            if (file.exists()) {// 判断文件存在不存在，存在再去读取
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024 * 5];
                    int len = 0;
                    while ((len = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, len);
                    }
                    return bos.toByteArray();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
