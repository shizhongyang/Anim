package com.bo.anim.md.recycler.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shixin on 2017-11-03.
 * author shixin
 *
 */

public class CustomBehavior extends CoordinatorLayout.Behavior {

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*用来决定需要监听哪些控件或者容器的状态（1.知道监听谁， 2.什么状态）
    * CoordinatorLayout parent,
    * View child,  观察者
    * View dependency  监听的那个View
    * */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //dependency.getTag();
        //dependency.getId();
       // ViewAnimationUtils.createCircularReveal()
        return dependency instanceof TextView || super.layoutDependsOn(parent, child, dependency);
    }


    //被监听的view发生改变的时候回调
    //可以在此方法里面做一些响应联动的动画效果
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //获取被监听View的状态 ----c垂直方向的位置
        int offset = dependency.getTop();
        //让child平移
        ViewCompat.offsetTopAndBottom(child,offset);
        return true;
    }
}
/*
<android.support.design.widget.CoordinatorLayout
xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.ricky.materialdesign.fab.animation.MainActivity" >

    <TextView
        android:id="@+id/tv1"
        android:tag="tv1"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:background="#ff0"
        android:layout_gravity="left|top"
        android:text="被观察--dependent" />
    <TextView
        app:layout_behavior="com.ricky.materialdesign.custombehavior.CustomBehavior"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_gravity="right|top"
        android:background="#f00"
        android:text="观察者" />
    <TextView
        app:layout_behavior="com.ricky.materialdesign.custombehavior.CustomBehavior"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_gravity="center"
        android:background="#f00"
        android:text="观察者" />

</android.support.design.widget.CoordinatorLayout>
*/