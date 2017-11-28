package com.bo.anim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bo.anim.entity.MyUser;
import com.bo.anim.entity.PackageInfo;
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

public class DBUserHelper extends OrmLiteSqliteOpenHelper {

    /**
     * 用来存放Dao的地图
     */
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DBUserHelper instance;
    private static final String DATABASENAME = "user.db";
    private static final int DATABASEVERSION = 1;
    public static final String DATABASE_PATH = SDCardUtil.getExternalSdCardPath()
            + File.separator + MyApplication.getAppName() + File.separator + DATABASENAME;

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static synchronized DBUserHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBUserHelper.class) {
                if (instance == null) {
                    instance = new DBUserHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法
     *
     * @param context
     */
    public DBUserHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        // 创建表
        try {
            Log.i("shi", "创建表" + database.getPath());
            TableUtils.createTableIfNotExists(connectionSource, PackageInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, MyUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        Log.i("shi", "onUpgrade: 更新表" + oldVersion + newVersion);
    }

    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (!daos.containsKey(className)) {
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


    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {

        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    public static void initUserDatabase(Context context) {
        File file = new File(SDCardUtil.getExternalSdCardPath(),MyApplication.getAppName()+File.separator+"db");
        if (!file.exists()){
            file.mkdirs();
        }
        Log.i("shi", "initUserDatabase: "+DATABASE_PATH);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                DATABASE_PATH, null);
        getHelper(context).onCreate(db);
        getHelper(context).onUpgrade(db,instance.connectionSource,1,1);
        db.close();
    }
}
