package com.bo.dblibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by TT on 2017-11-09.
 */

public class BaseDaoFactory {

    private String sqliteDatabasePath;

    private SQLiteDatabase sqLiteDatabase;

    private static  BaseDaoFactory instance=new BaseDaoFactory();

    private BaseDaoFactory() {
        sqliteDatabasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shi.db";
        openDatabase();
    }

    public synchronized  <T extends BaseDao<M>, M> T
    getDataHelper(Class<T> clazz,Class<M> entityClass) {
        BaseDao baseDao = null;

        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClass,sqLiteDatabase);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) baseDao;
    }


    private void openDatabase() {
        this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    public  static  BaseDaoFactory getInstance()
    {
        return instance;
    }
}