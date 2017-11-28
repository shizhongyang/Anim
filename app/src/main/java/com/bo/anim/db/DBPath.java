package com.bo.anim.db;

import android.util.Log;

import com.bo.anim.db.dao.MyUserDao;
import com.bo.anim.entity.MyUser;
import com.bo.anim.ui.MyApplication;
import com.bo.dblibrary.db.tools.SDCardUtil;

import java.io.File;

/**
 * Created by TT on 2017-11-16.
 */

public enum DBPath {


    /**
     * 存放本地数据库的路径
     */
    database("local/data/database/");

    /**
     * 文件存储的文件路径
     */
    private String value;
    DBPath(String value )
    {
        this.value = value;
    }

    public String getValue() {
        MyUser currentUser = MyUserDao.getCurrentUser();
        if(currentUser!=null)
        {
            File file=new File(SDCardUtil.getExternalSdCardPath(), MyApplication.getAppName());
            if(!file.exists())
            {
                file.mkdirs();
            }

            File file1 = new File(file.getAbsolutePath(),currentUser.userId);
            if(!file1.exists())
            {
                file1.mkdirs();
            }

            String path = file1.getAbsolutePath() + File.separator + "Login.db";
            Log.i("shi", "getValue: "+path);
            return path;
        }

        return value;
    }
}
