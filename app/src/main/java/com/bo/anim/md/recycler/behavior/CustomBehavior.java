package com.bo.anim.md.recycler.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by TT on 2017-11-03.
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
