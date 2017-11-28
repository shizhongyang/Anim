package com.bo.anim.ui.db;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.db.DBHelper;
import com.bo.anim.db.DBUserHelper;
import com.bo.anim.db.dao.MyUserDao;
import com.bo.anim.entity.MyUser;
import com.bo.dblibrary.db.BaseDaoFactory;
import com.bo.dblibrary.db.IBaseDao;
import com.bo.dblibrary.db.test.User;
import com.bo.dblibrary.db.test.UserDao;
import com.bo.dblibrary.db.tools.SDCardUtil;

import java.io.File;

public class DBTestActivity extends Activity {
    IBaseDao<User> userDao;
    private MyUserDao myUserDao;
    private int id;
    private final static String TAG = "shi";


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        userDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);

        DBUserHelper.initUserDatabase(getApplicationContext());
        myUserDao = new MyUserDao(this);

        File file = new File(SDCardUtil.getExternalSdCardPath()
                +File.separator+"69eaa6be39f254483ed555bdff1673c0v0");
        System.out.println(file.length());
        Bitmap bitmap=BitmapFactory.decodeFile(SDCardUtil.getExternalSdCardPath()
                +File.separator+"69eaa6be39f254483ed555bdff1673c0v0",getBitmapOption(2)); //将图片的长和宽缩小味原来的1/2
        System.out.println(bitmap.getWidth());
    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                User user = new User("4", "5");
                Long d = userDao.insert(user);
                System.out.println(":-----" + d);
                break;
            case R.id.btn2:
                System.out.println("----" + DBUserHelper.DATABASE_PATH);

                break;
            case R.id.btn3:
                MyUser users = new MyUser("M00" + id++, "shixin");
                users.status = true;
                int result = myUserDao.addOrUpdate(users);
                Log.i(TAG, "click: result"+result);
                if (result == 1){
                    DBHelper.init(getApplicationContext());
                }
                break;
        }
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize)

    {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
}
