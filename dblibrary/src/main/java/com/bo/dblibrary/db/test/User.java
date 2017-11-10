package com.bo.dblibrary.db.test;

import com.bo.dblibrary.db.annotation.DBField;
import com.bo.dblibrary.db.annotation.DBTable;

/**
 * Created by TT on 2017-11-10.
 */
@DBTable("tb_user")
public class User {

    @DBField("name")
    public String name;

    @DBField("password")
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
