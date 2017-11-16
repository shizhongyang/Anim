package com.bo.anim.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by TT on 2017-11-16.
 */

@DatabaseTable
public class PackageInfo implements Serializable {
    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String pid;
    @DatabaseField
    public String photographerId;
    @DatabaseField
    public String name;
    @DatabaseField()
    public int cost;
    @DatabaseField
    public String description;
    @DatabaseField
    public String detail;


    @Override
    public String toString() {
        return "Package [id=" + id + ", pid=" + pid + ", photographerId="
                + photographerId + ", name=" + name + ", cost=" + cost
                + ", description=" + description + ", detail=" + detail + "]";
    }

}