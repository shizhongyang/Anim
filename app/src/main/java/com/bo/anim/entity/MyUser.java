package com.bo.anim.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by TT on 2017-11-16.
 */
@DatabaseTable
public class MyUser implements Serializable{

    public MyUser() {
    }

    @DatabaseField(dataType = DataType.INTEGER, generatedId = true)
    public int id;

    @DatabaseField
    public String userId;

    @DatabaseField
    public String userName;

    @DatabaseField
    public boolean status;

    public MyUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
