package com.bo.dblibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bo.dblibrary.db.annotation.DBField;
import com.bo.dblibrary.db.annotation.DBTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TT on 2017-11-09.
 */

public abstract class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase sqLiteDatabase;
    private Class<T> entityClass;
    private boolean isInit = false;
    private String tableName;
    private Map<String, Field> cacheMap;

    protected synchronized boolean init(Class<T> entityClass, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {

            this.sqLiteDatabase = sqLiteDatabase;
            this.entityClass = entityClass;
            if (entityClass.getAnnotation(DBTable.class) == null) {
                tableName = entityClass.getSimpleName();
            } else {
                tableName = entityClass.getAnnotation(DBTable.class).value();
            }
            cacheMap = new HashMap<>();
            initCacheMap();
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            if (!TextUtils.isEmpty(tableName)) {
                sqLiteDatabase.execSQL(createTable());
            }
            isInit = true;

        }
        return isInit;
    }

    //维护映射关系,比如表的字段名是tb_name,类字段名是name 要将他们对应起来，根据注解
    private void initCacheMap() {
        String sql = "select * from " + tableName + " limit 1 , 0";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
            //表的列名数组
            String[] columnNames = cursor.getColumnNames();

            //类中的Filed的数组
            Field[] fields = entityClass.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            //开始找对应关系
            for (String columnName : columnNames) {
                //如果找到对应的Filed就赋值给他
                Field columnField = null;
                for (Field field : fields) {
                    String fieldName = null;
                    if (field.getAnnotation(DBField.class) != null) {
                        fieldName = field.getAnnotation(DBField.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }
                //找到了对应关系
                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValues(entity);

        ContentValues contentValues = getContentValues(map);
        if (contentValues == null){
            return (long) -1;
        }
        Long result = sqLiteDatabase.insert(tableName, null, contentValues);
        return result;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        if (map == null){
            return null;
        }
        for (String s : map.keySet()) {
            String value = map.get(s);
            if (value != null) {
                contentValues.put(s, value);
            }
        }
        return contentValues;
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> result = new HashMap<>();
        System.out.println("cacheMap---------"+cacheMap.size());
        if (cacheMap == null){
            System.out.println("cacheMap---------"+cacheMap.size());
            return null;
        }
        for (Field field : cacheMap.values()) {

            String cacheKey;
            String cacheValue = null;
            if (field.getAnnotation(DBField.class) != null) {
                cacheKey = field.getAnnotation(DBField.class).value();
            } else {
                cacheKey = field.getName();
            }

            try {
                if (null == field.get(entity)) {
                    continue;
                }
                cacheValue = field.get(entity).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.println("---"+cacheKey+cacheValue);
            result.put(cacheKey, cacheValue);
        }
        return result;

    }

    @Override
    public int update(T entity, T where) {
        return 0;
    }


    protected abstract String createTable();
}
