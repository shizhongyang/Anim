package com.bo.anim.md.recycler.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

public class SyncScrollBehavior extends Behavior<View> {

	public SyncScrollBehavior(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
			View child, View directTargetChild, View target,
			int nestedScrollAxes) {
		// TODO Auto-generated method stub
		return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL)||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
				target, nestedScrollAxes);
	}
	
	@Override
	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
			View child, View target, int dx, int dy, int[] consumed) {
		// TODO Auto-generated method stub
		int scrollY = target.getScrollY();
		child.setScrollY(scrollY);
		super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
	}
	
	@Override
	public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
			View child, View target, float velocityX, float velocityY,
			boolean consumed) {
		// 快速滑动的惯性移动（松开手指后还会有滑动效果）
		((NestedScrollView)child).fling((int) velocityY);
		return super.onNestedFling(coordinatorLayout, child, target, velocityX,
				velocityY, consumed);
	}
	
}
