package com.bo.anim.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by TT on 2017-11-16.
 */
@DatabaseTable
public class User implements Serializable{

    @DatabaseField(id = true)
    public int id;

    @DatabaseField
    public String userId;

    @DatabaseField
    public String userName;
}
