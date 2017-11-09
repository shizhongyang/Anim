package com.bo.dblibrary.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bo.dblibrary.db.annotation.DBField;
import com.bo.dblibrary.db.annotation.DBTable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by TT on 2017-11-09.
 */

public abstract class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase sqLiteDatabase;
    private Class<T> entityClass;
    private boolean isInit;
    private String tableName;
    private HashMap<String,Field> cacheMap;
    protected synchronized boolean init(Class<T> entityClass, SQLiteDatabase sqLiteDatabase) {
        if (isInit){
            this.sqLiteDatabase = sqLiteDatabase;
            this.entityClass = entityClass;
            if (entityClass.getAnnotation(DBTable.class)==null)
            {
                tableName = entityClass.getSimpleName();
            }else {
                tableName = entityClass.getAnnotation(DBTable.class).value();
            }

            if (!sqLiteDatabase.isOpen()){
                return false;
            }

            if (!TextUtils.isEmpty(tableName)){
                sqLiteDatabase.execSQL(createTable());
            }

            cacheMap = new HashMap<>();

            initCacheMap();
            isInit = true;

        }

        return isInit;

    }

    //维护映射关系
    private void initCacheMap() {
        String sql = "select * from " + tableName +"limit 1 , 0";
        Cursor cursor = null;

        try{
            cursor = sqLiteDatabase.rawQuery(sql,null);

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
                    if (field.getAnnotation(DBField.class)!=null) {
                        fieldName = field.getAnnotation(DBField.class).value();
                    }else {
                        fieldName = field.getName();
                    }
                    if (columnName.equals(fieldName)){
                        columnField = field;
                        break;
                    }
                }
                //找到了对应关系
                if (columnField!=null){
                    cacheMap.put(columnName,columnField);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }



    }


    @Override
    public Long insert(T entity) {
        return null;

    }

    @Override
    public int update(T entity, T where) {
        return 0;

    }



    protected abstract String createTable();
}
