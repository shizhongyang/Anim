package com.bo.anim.md.weibo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by TT on 2017-11-28.
 */

public class NestedLinearLayout extends LinearLayout implements NestedScrollingChild {

    private static final String TAG = "NestedLinearLayout";

    private final int[] offset = new int[2];
    private final int[] consumed = new int[2];

    private int lastY;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];

    private VelocityTracker mVelocityTracker;
    private int mLastTouchX;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastTouchY;
    private int mTouchSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;

    private NestedScrollingChildHelper mScrollingChildHelper;
    private int mActivePointerId;
    private boolean mIsBeingDragged;

    public NestedLinearLayout(Context context) {
        this(context, null);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
            mScrollingChildHelper.setNestedScrollingEnabled(true);
        }

        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();

    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        Log.i(TAG, "onInterceptTouchEvent: actionIndex");
        final boolean canScrollHorizontally = canScrollHorizontally(HORIZONTAL);
        final boolean canScrollVertically = canScrollVertically(VERTICAL);

        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = e.getPointerId(0);
                mInitialTouchX = mLastTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY() + 0.5f);

                // Clear the nested offsets
                mNestedOffsets[0] = mNestedOffsets[1] = 0;

                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(e);


                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                if (canScrollHorizontally) {
                    nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                }
                if (canScrollVertically) {
                    nestedScrollAxis |= ViewCompat.SCROLL_AXIS_VERTICAL;
                }
                startNestedScroll(nestedScrollAxis);
                Log.i(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                // 当开始滑动的时候，告诉父view
                /*startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL
                        | ViewCompat.SCROLL_AXIS_VERTICAL);*/
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN:

                mActivePointerId = e.getPointerId(actionIndex);
                mInitialTouchX = mLastTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointerIndex = e.findPointerIndex(mActivePointerId);

                final int y = (int) e.getY(pointerIndex);
                final int yDiff = Math.abs(y - mLastTouchY);

                if (yDiff > mTouchSlop) {
                    mIsBeingDragged = true;
                    initVelocityTrackerIfNotExists();
                    mVelocityTracker.addMovement(e);
                }
                Log.i(TAG, "onInterceptTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mVelocityTracker.clear();
                stopNestedScroll();
                Log.i(TAG, "onInterceptTouchEvent: actionIndex" + actionIndex);
                break;
        }
        return mIsBeingDragged;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean canScrollHorizontally = canScrollHorizontally(HORIZONTAL);
        final boolean canScrollVertically = canScrollVertically(VERTICAL);
        //Log.i(TAG, "onTouchEvent: HORIZONTAL = " + canScrollHorizontally + "VERTICAL=" + canScrollVertically);

        MotionEvent vtev = MotionEvent.obtain(event);
        final int action = MotionEventCompat.getActionMasked(event);
        final int actionIndex = MotionEventCompat.getActionIndex(event);

        //Log.i(TAG, "onTouchEvent: actionIndex" + actionIndex);
        if (action == MotionEvent.ACTION_DOWN) {
            mNestedOffsets[0] = mNestedOffsets[1] = 0;
        }
        vtev.offsetLocation(mNestedOffsets[0], mNestedOffsets[1]);


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                Log.i(TAG, "onTouchEvent: mActivePointerId" + mActivePointerId);
                mInitialTouchX = mLastTouchX = (int) (event.getX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (event.getY() + 0.5f);

                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                if (canScrollHorizontally) {
                    nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
                }
                if (canScrollVertically) {
                    nestedScrollAxis |= ViewCompat.SCROLL_AXIS_VERTICAL;
                }
                startNestedScroll(nestedScrollAxis);
                lastY = (int) event.getRawY();
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mActivePointerId = event.getPointerId(actionIndex);
                mInitialTouchX = mLastTouchX = (int) (event.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (event.getY(actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                final int index = event.findPointerIndex(mActivePointerId);
                //final int index = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id " +
                            mActivePointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x1 = (int) (event.getX(index) + 0.5f);
                final int y1 = (int) (event.getY(index) + 0.5f);
                int dx1 = mLastTouchX - x1;
                int dy1 = mLastTouchY - y1;

                if (dispatchNestedPreScroll(dx1, dy1, mScrollConsumed, mScrollOffset)) {
                    dx1 -= mScrollConsumed[0];
                    dy1 -= mScrollConsumed[1];
                    vtev.offsetLocation(mScrollOffset[0], mScrollOffset[1]);
                    // Updated the nested offsets
                    mNestedOffsets[0] += mScrollOffset[0];
                    mNestedOffsets[1] += mScrollOffset[1];

                    //Log.i(TAG, "MotionEventCompat: lastY=" + dx1 + "-" + dy1);
                }

              /*int y = (int) (event.getRawY());
                int dy = lastY - y;
                lastY = y;
                Log.i(TAG, "onTouchEvent: lastY=" + lastY);
                Log.i(TAG, "onTouchEvent: dy=" + dy);
                //  dy < 0 下拉， dy>0 上滑
                if (dy > 0) { // 上滑的时候才交给父类去处理
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                            && dispatchNestedPreScroll(0, dy, consumed, offset)) {
                        // 父类进行了一部分滚动
                    }
                } else {
                    Log.i(TAG, "onTouchEvent: dy<= 0");
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                            && dispatchNestedPreScroll(0, dy, consumed, offset)) {
                        // 父类进行了一部分滚动
                    }
                }*/
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(event);
                break;
            case MotionEvent.ACTION_UP:

                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker,
                        mActivePointerId);
                Log.i(TAG, "onTouchEvent: ACTION_UP" + initialVelocity);

                if ((Math.abs(initialVelocity) > mMinFlingVelocity)) {
                    flingWithNestedDispatch(-initialVelocity);
                }
                break;
        }
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }


    private void flingWithNestedDispatch(int velocityY) {
        final int scrollY = getScrollY();
        Log.i(TAG, "onTouchEvent: ACTION_UP" + scrollY);
        final boolean canFling = (scrollY > 0 || velocityY > 0)
                && (scrollY < 0 || velocityY < 0);
        if (!dispatchNestedPreFling(0, velocityY)) {
            dispatchNestedFling(0, velocityY, canFling);
        }
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (e.getPointerId(actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = e.getPointerId(newIndex);
            mInitialTouchX = mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
            mInitialTouchY = mLastTouchY = (int) (e.getY(newIndex) + 0.5f);

            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }


    @Override
    public boolean canScrollVertically(int direction) {
        return true;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }


    private NestedScrollingChildHelper getScrollingChildHelper() {
        return mScrollingChildHelper;
    }

    // 接口实现--------------------------------------------------

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed,
                                           int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy,
                consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY,
                                       boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX,
                velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX,
                velocityY);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}


