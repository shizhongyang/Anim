package com.bo.anim.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by TT on 2017-11-16.
 */

public class SQLiteHelperOrm extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_PATH = Environment
            .getExternalStorageDirectory() + "/test.db";

    public SQLiteHelperOrm(Context context) {
        super(context, null, null, 0);

    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
