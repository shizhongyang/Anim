package com.bo.dblibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bo.dblibrary.db.tools.SDCardUtil;

/**
 * Created by TT on 2017-11-09.
 */

public class BaseDaoFactory {

    private String sqliteDatabasePath;

    private SQLiteDatabase sqLiteDatabase;

    private static BaseDaoFactory instance = new BaseDaoFactory();

    private BaseDaoFactory() {
        sqliteDatabasePath = SDCardUtil.getExternalSdCardPath() + "/shi.db";
        Log.i("shixin",sqliteDatabasePath);
        openDatabase();
    }


    public synchronized <T extends BaseDao<M>, M> T getDataHelper(Class<T> clazz, Class<M> entityClass) {
        BaseDao<M> baseDao = null;
        try {
            //利用反射来获取对象
            baseDao = (BaseDao<M>)clazz.newInstance();
            baseDao.init(entityClass, sqLiteDatabase);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        @SuppressWarnings("unchecked")
        T dao = (T) baseDao;
        return dao;
    }


    private void openDatabase() {
        this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);

    }

    public static BaseDaoFactory getInstance() {
        return instance;
    }
}
