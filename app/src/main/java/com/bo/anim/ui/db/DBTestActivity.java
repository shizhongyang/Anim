package com.bo.anim.ui.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.db.DBHelper;
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

        userDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);
    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                User user = new User("4", "5");
                Long d = userDao.insert(user);
                System.out.println(":-----" + d);
                break;
            case R.id.btn2:
                System.out.println("----"+DBHelper.DATABASE_PATH);
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                        DBHelper.DATABASE_PATH, null);
                DBHelper orm = new DBHelper(this);
                orm.onCreate(db);
                db.close();
                break;
        }
    }
}
