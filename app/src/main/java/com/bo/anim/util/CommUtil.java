package com.bo.anim.util;

import android.content.Context;

/**
 * Created by ricky on 2016/11/2.
 */

public class CommUtil {
    private static CommUtil instance;
    private Context context;
    private CommUtil(Context context){
        this.context = context;
    }

    public static CommUtil getInstance(Context mcontext){
        if(instance == null){
            instance = new CommUtil(mcontext);
        }
//        else{
//            instance.setContext(mcontext);
//        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
