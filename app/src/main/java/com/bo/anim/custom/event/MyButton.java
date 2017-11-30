package com.bo.anim.custom.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by TT on 2017-10-20.
 */

public class MyButton extends Button implements View.OnTouchListener{
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }


    //返回true不再分发 也不会调用父类的方法 一般不会动这个方法
    //返回false也不再分发  但是会调用父类的方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("---------------MyButton的dispatchTouchEvent被调用");
       // return false;
        return super.dispatchTouchEvent(event);
    }

    //优先级高于 onTouchEvent
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    //这里面返回false代表事件没有被消耗  将会转交给上层ViewGroup的onTouchEvent,
    //如果这里面返回的是true 则会交给自己

    // 返回true和false onClickListener方法将不会被调用
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("---------------MyButton的onTouchEvent被调用");
        return super.onTouchEvent(event);
        //return true;
    }

    /*
    * 返回false
        ----ViewGroup dispatchTouchEvent
        ----ViewGroup onInterceptTouchEvent
        ----ViewGroup1 dispatchTouchEvent
        ----ViewGroup1 onInterceptTouchEvent
        ---------------MyButton的dispatchTouchEvent被调用
        ---------------MyButton的onToucEvent被调用
        ----ViewGroup1 onTouchEvent
        ----ViewGroup onTouchEvent
    * */


    /* 返回true
     ----ViewGroup dispatchTouchEvent
     ----ViewGroup onInterceptTouchEvent
     ----ViewGroup1 dispatchTouchEvent
     ----ViewGroup1 onInterceptTouchEvent
     ---------------MyButton的dispatchTouchEvent被调用
     ---------------MyButton的onToucEvent被调用
     */

}
