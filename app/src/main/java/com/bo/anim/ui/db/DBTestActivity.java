package com.bo.anim.ui.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bo.anim.R;
import com.bo.dblibrary.db.BaseDaoFactory;
import com.bo.dblibrary.db.IBaseDao;
import com.bo.dblibrary.db.test.User;
import com.bo.dblibrary.db.test.UserDao;

public class DBTestActivity extends Activity {
    IBaseDao<User> userDao;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        userDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class,User.class);
    }

    public void click(View view){
        User user = new User("4","5");

        Long d = userDao.insert(user);
        System.out.println(":-----"+d);
    }
}
