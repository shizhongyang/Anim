package com.bo.anim.db.dao;

import android.content.Context;
import android.util.Log;

import com.bo.anim.db.DBUserHelper;
import com.bo.anim.entity.MyUser;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by TT on 2017-11-17.
 */

public class MyUserDao {

    private final String TAG = this.getClass().getSimpleName().toLowerCase();
    private static Dao<MyUser, Integer> userDao;
    private DBUserHelper dbHelper;

    public MyUserDao(Context context) {
        try {
            dbHelper = DBUserHelper.getHelper(context);
            userDao = dbHelper.getDao(MyUser.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //只在登录和退出的时候调用
    public int  addOrUpdate(MyUser user) {
        int result = -1;
        try {
            Log.i(TAG, "add: " + userDao);
            //userDao.queryForEq()

            //查询列表中是否有这个人
            List<MyUser> users = userDao.queryForEq("userId", user.userId);

            //将其他人状态设置为没有登录
            List<MyUser> myUsers = userDao.queryForAll();
            for (MyUser myUser : myUsers) {
                myUser.status = false;
                userDao.update(myUser);
            }
            //清空内存
            myUsers.clear();


            if (users.size() == 0) {
                result = userDao.create(user);
                return result;
            }else {
                System.out.println("----------"+user.userId);
                for (MyUser myUser : users) {
                    myUser.status = true;
                    result =  userDao.update(myUser);
                }
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static MyUser getCurrentUser(){
        try {
            List<MyUser> users = userDao.queryForEq("status", true);
            if (users.size()!=0){
                return users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
