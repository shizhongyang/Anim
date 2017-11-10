package com.bo.dblibrary.db.test;

import com.bo.dblibrary.db.BaseDao;

/**
 * Created by TT on 2017-11-10.
 */

public class UserDao extends BaseDao{
    @Override
    protected String createTable() {
        return "create table if not exists tb_user(name varchar(20),password varchar(10))";
    }
}
