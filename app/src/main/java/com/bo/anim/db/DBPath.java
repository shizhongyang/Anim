package com.bo.anim.db;

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
        return value;
    }
}
