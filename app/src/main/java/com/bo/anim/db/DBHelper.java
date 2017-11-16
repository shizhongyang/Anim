package com.bo.anim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bo.anim.entity.PackageInfo;
import com.bo.anim.entity.User;
import com.bo.anim.ui.MyApplication;
import com.bo.dblibrary.db.tools.SDCardUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TT on 2017-11-15.
 * {}
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    /**
     * 数据库名字
     */
    private static final String DB_NAME = "test.db";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;


    /**
     * 用来存放Dao的地图
     */
    private Map<String, Dao> daos = new HashMap<String, Dao>();


    private static DBHelper instance;

    private Context context;



    /**
     * 获取单例
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法
     * @param context
     */
    public DBHelper(Context context) {
        super(context, null, null, 1);
        this.context = context;
    }


    public DBHelper(Context context, String databaseName,
                    SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        // 创建表
        try {
            Log.i("shi","创建表");
            TableUtils.createTableIfNotExists(connectionSource, PackageInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            //TableUtils.createTable(connectionSource, Photographer.class);
            //TableUtils.createTable(connectionSource, Theme.class);
            //TableUtils.createTable(connectionSource, Img.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;

        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }



    public static final String DATABASE_PATH = SDCardUtil.getExternalSdCardPath()
            + File.separator+ MyApplication.getAppName()+ File.separator+"test.db";

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {

        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READONLY);
    }
}
