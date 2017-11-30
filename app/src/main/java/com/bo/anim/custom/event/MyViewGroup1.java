package com.bo.anim.custom.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by TT on 2017-10-20.
 */

public class MyViewGroup1 extends RelativeLayout {

    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("----ViewGroup1 dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    //这里面拦截了以后将不会执行子View的事件分发
    //如果当前视图不消耗事件 会将事件传递给父视图
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("----ViewGroup1 onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("----ViewGroup1 onTouchEvent");
//        return super.onTouchEvent(event);
        return false;
    }

    //子view返回false 这里返回true会一直调用两个方法，
    //只是在点击的时候会调用本类中的onInterceptTouchEvent方法，子类不消耗交给父类的时候也不会调用这个方法
    //但是当前视图的父View会调用onInterceptTouchEvent和dispatchTouchEvent 循环调用
    /* ----ViewGroup dispatchTouchEvent
     ----ViewGroup onInterceptTouchEvent
     ----ViewGroup1 dispatchTouchEvent
     ----ViewGroup1 onInterceptTouchEvent
     ---------------MyButton的dispatchTouchEvent被调用
     ---------------MyButton的onTouchEvent被调用
     ----ViewGroup1 onTouchEvent
     ----ViewGroup dispatchTouchEvent
     ----ViewGroup onInterceptTouchEvent
     ----ViewGroup1 dispatchTouchEvent
     ----ViewGroup1 onTouchEvent
     ----ViewGroup dispatchTouchEvent
     ----ViewGroup onInterceptTouchEvent
     ----ViewGroup1 dispatchTouchEvent
     ----ViewGroup1 onTouchEvent*/

    /*
      视图书中View的onTouchEvent全部返回false的时候，这些方法只执行一次
     ----ViewGroup dispatchTouchEvent
     ----ViewGroup onInterceptTouchEvent
     ----ViewGroup1 dispatchTouchEvent
     ----ViewGroup1 onInterceptTouchEvent
     ---------------MyButton的dispatchTouchEvent被调用
     ---------------MyButton的onTouchEvent被调用
     ----ViewGroup1 onTouchEvent
     ----ViewGroup onTouchEvent*/

}
