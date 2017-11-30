package com.bo.anim.md.recycler.scrollfab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by TT on 2017-11-02.
 */

public class FABBehavior extends FloatingActionButton.Behavior {
    private boolean visible = true;//是否可见


    public FABBehavior() {
    }


    //这个构造方法不能少，看源码可以得知。
    public FABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //页面开始滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {

        //当观察的View（RecyclerView） 发生滑懂开始的时候回调的
        //我们这里只关心垂直的滑动 nestedScrollAxes：滑动关联轴
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child,
                directTargetChild, target, nestedScrollAxes);

    }

    //        if (dyConsumed > 0 && dyUnconsumed == 0) {
//            System.out.println("上滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed > 0) {
//            System.out.println("到边界了还在上滑。。。");
//        }
//        if (dyConsumed < 0 && dyUnconsumed == 0) {
//            System.out.println("下滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed < 0) {
//            System.out.println("到边界了，还在下滑。。。");
//        }

    //页面正在滑动
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child,
                target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //当观察view滑动的时候回调
        if (dyConsumed > 0 && visible) {
            visible = false;
            onHide(child);
        } else if (dyConsumed < 0 && !visible) {
            visible = true;
            onShow(child);
        }
    }


    //页面停止滑动
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

    }

    private void onShow(FloatingActionButton child) {
        child.animate().translationY(0).setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        ViewCompat.animate(child).scaleX(1f).scaleY(1f).start();
    }

    private void onHide(FloatingActionButton child) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                child.getLayoutParams();
        child.animate().translationY(child.getHeight()+layoutParams.bottomMargin).setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        ViewCompat.animate(child).scaleX(0f).scaleY(0f).start();
    }

}
