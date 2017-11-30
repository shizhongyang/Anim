package com.bo.anim.md.weibo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bo.anim.BuildConfig;
import com.bo.anim.R;
import com.bo.anim.md.weibo.android.HeaderScrollingViewBehavior;
import com.bo.anim.ui.MyApplication;

import java.util.List;

/**
 * Created by TT on 2017-11-28.
 *
 */

public class ContentBehavior extends HeaderScrollingViewBehavior {

    private final String TAG = getClass().getSimpleName();

    public ContentBehavior() {
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency) || super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDependentViewChanged");
        }
        offsetChildAsNeeded(parent, child, dependency);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {

        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();

        float dependencyTranslationY = dependency.getTranslationY();
        int  translationY = (int) (-dependencyTranslationY / (getHeaderOffsetRange() * 1.0f)
                * getScrollRange(dependency));
        Log.i(TAG, "offsetChildAsNeeded: translationY=" + translationY);
        //child.setTranslationY(translationY);
        ViewCompat.setTranslationY(child, translationY);
    }

    @Override
    protected int getScrollRange(View v) {
        if (isDependOn(v)) {
            return Math.max(0, v.getMeasuredHeight() - getFinalHeight());
        }
        return super.getScrollRange(v);
    }

    private int getFinalHeight() {
        return 0;
    }


    private int getHeaderOffsetRange() {
        return -MyApplication.getInstance().getResources().getDimensionPixelOffset(R.dimen.header_page_height_dp);
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view)) {
                return view;
            }
        }
        return null;
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.id_weibo_header;
    }
}
