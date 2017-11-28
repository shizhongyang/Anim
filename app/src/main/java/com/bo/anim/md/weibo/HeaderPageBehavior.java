package com.bo.anim.md.weibo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.bo.anim.BuildConfig;
import com.bo.anim.R;
import com.bo.anim.md.weibo.android.ViewOffsetBehavior;
import com.bo.anim.ui.MyApplication;

import java.lang.ref.WeakReference;

/**
 * Created by TT on 2017-11-28.
 */

public class HeaderPageBehavior extends ViewOffsetBehavior {

    private static final String TAG = "HeaderPageBehavior";
    private static final int STATE_OPENED = 0;
    private static final int STATE_CLOSED = 1;
    private static final int DURATION_SHORT = 300;
    private static final int DURATION_LONG = 600;

    private int mCurrState = STATE_OPENED;
    private boolean isClosed;

    //滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间
    //mScroller.startScroll(int startX, int startY, int dx, int dy) //使用默认完成时间250ms
    //mScroller.startScroll(int startX, int startY, int dx, int dy, int duration)
    private OverScroller mOverScroller;
    private OnPagerStateListener mPagerStateListener;

    private WeakReference<CoordinatorLayout> mParent;
    private WeakReference<View> mChild;


    public HeaderPageBehavior() {
        init();
    }

    public HeaderPageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(MyApplication.getInstance());
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        boolean closed = isClosed();
        Log.i(TAG, "onInterceptTouchEvent: closed=" + closed);
        if (ev.getAction() == MotionEvent.ACTION_UP && !closed) {
            handleActionUp(parent, child);
        }
        return super.onInterceptTouchEvent(parent, child, ev);

    }


    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<CoordinatorLayout>(parent);
        mChild = new WeakReference<View>(child);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStartNestedScroll: nestedScrollAxes=" + nestedScrollAxes);
        }
        boolean canScroll = canScroll(child, 0);
        return ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll && !isClosed(child));
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (!isClosed) {
            //dy>0 scroll up;dy<0,scroll down
            Log.i(TAG, "onNestedPreScroll: dy=" + dy);
            float halfOfDis = dy;
            //不能滑动了
            if (!canScroll(child, halfOfDis)) {
                child.setTranslationY(halfOfDis > 0 ? getHeaderOffsetRange() : 0);
            } else {
                child.setTranslationY(child.getTranslationY() - halfOfDis);
            }
            //consumed all scroll behavior after we started Nested Scrolling
            consumed[1] = dy;
            Log.i(TAG, "isClosed: " + isClosed);
            isClosed = isClosed(child);
            changeState(isClosed ? STATE_CLOSED : STATE_OPENED);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target,
                                    float velocityX, float velocityY) {
        // consumed the flinging behavior until Closed

        boolean coumsed = !isClosed(child);
        Log.i(TAG, "onNestedPreFling: coumsed=" + coumsed);
        return coumsed;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target,
                                 float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG, "onNestedFling: velocityY=" + velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY,
                consumed);

    }

    private int getHeaderOffsetRange() {
        return -MyApplication.getInstance().getResources()
                .getDimensionPixelOffset(R.dimen.two_hundred_dp);
    }

    public boolean isClosed() {
        return mCurrState == STATE_CLOSED;
    }

    private boolean isClosed(View child) {
        return child.getTranslationY() == getHeaderOffsetRange();
    }

    // 表示 Header TransLationY 的值是否达到我们指定的阀值，
    // headerOffsetRange到达了，返回 false，
    // 否则返回true。注意 TransLationY 是负数。
    private boolean canScroll(View child, float pendingDy) {

        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        int headerOffsetRange = getHeaderOffsetRange();
        return pendingTranslationY >= headerOffsetRange && pendingTranslationY <= 0;
    }


    private void changeState(int newState) {
        if (mCurrState != newState) {
            mCurrState = newState;

            if (mCurrState == STATE_OPENED) {
                if (mPagerStateListener != null) {
                    mPagerStateListener.onPagerOpened();
                }
            } else {
                if (mPagerStateListener != null) {
                    mPagerStateListener.onPagerClosed();
                }
            }
        }
    }

    private void onFlingFinished(CoordinatorLayout coordinatorLayout, View layout) {
        changeState(isClosed(layout) ? STATE_CLOSED : STATE_OPENED);
    }

    public void setmPagerStateListener(OnPagerStateListener mPagerStateListener) {
        this.mPagerStateListener = mPagerStateListener;
    }



    private FlingRunnable mFlingRunnable;
    private void handleActionUp(CoordinatorLayout parent, View child) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "handleActionUp: ");
        }
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        mFlingRunnable = new FlingRunnable(parent, child);
        Log.i(TAG, "handleActionUp: " + child.getTranslationY() + "---" + getHeaderOffsetRange() / 2.0f);
        if (child.getTranslationY() < getHeaderOffsetRange() / 2.0f) {
            mFlingRunnable.scrollToClosed(DURATION_SHORT);
        } else {
            mFlingRunnable.scrollToOpen(DURATION_SHORT);
        }
    }




    public void openPager() {
        openPager(DURATION_LONG);
    }


    /**
     * @param duration open animation duration
     */
    private void openPager(int duration) {
        View child = mChild.get();
        CoordinatorLayout parent = mParent.get();
        if (isClosed() && child != null) {
            if (mFlingRunnable != null) {
                child.removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            mFlingRunnable = new FlingRunnable(parent, child);
            mFlingRunnable.scrollToOpen(duration);
            isClosed = false;
        }
    }

    public void closePager() {
        closePager(DURATION_LONG);
    }

    /**
     * @param duration close animation duration
     */
    private void closePager(int duration) {
        View child = mChild.get();
        CoordinatorLayout parent = mParent.get();
        if (!isClosed()) {
            if (mFlingRunnable != null) {
                child.removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            mFlingRunnable = new FlingRunnable(parent, child);
            mFlingRunnable.scrollToClosed(duration);
        }

    }




    private class FlingRunnable implements Runnable {

        private final CoordinatorLayout mParent;
        private final View mLayout;

        FlingRunnable(CoordinatorLayout parent, View layout) {
            mParent = parent;
            mLayout = layout;
        }


        //mScroller.computeScrollOffset() //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。
        // 这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
        private void start() {
            if (mOverScroller.computeScrollOffset()) {
                mFlingRunnable = new FlingRunnable(mParent, mLayout);
                ViewCompat.postOnAnimation(mLayout, mFlingRunnable);
            } else {
                onFlingFinished(mParent, mLayout);
            }
        }

        void scrollToOpen(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
            start();
        }

        void scrollToClosed(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            float dy = getHeaderOffsetRange() - curTranslationY;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "scrollToClosed:offest:" + getHeaderOffsetRange());
                Log.d(TAG, "scrollToClosed: cur0:" + curTranslationY + ",end0:" + dy);
                Log.d(TAG, "scrollToClosed: cur:" + Math.round(curTranslationY) + ",end:" + Math
                        .round(dy));
                Log.d(TAG, "scrollToClosed: cur1:" + (int) (curTranslationY) + ",end:" + (int) dy);
            }
            mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy +
                    0.1f), duration);
            start();
        }


        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "run: " + mOverScroller.getCurrY());
                    }
                    ViewCompat.setTranslationY(mLayout, mOverScroller.getCurrY());
                    //循环调用  直到达到一定的条件
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                    onFlingFinished(mParent, mLayout);
                }
            }
        }
    }
}
