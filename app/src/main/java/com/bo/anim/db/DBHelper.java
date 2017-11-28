package com.bo.anim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;



/**
 * Created by TT on 2017-11-17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "Login.db";
    private static final int databaseVersion = 1;
    private static DBHelper instance;

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBUserHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DBPath.database.getValue(), null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DBPath.database.getValue(), null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public static void init(Context context){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                DBPath.database.getValue(), null);
        getHelper(context).onCreate(db);
        db.close();
    }
}
