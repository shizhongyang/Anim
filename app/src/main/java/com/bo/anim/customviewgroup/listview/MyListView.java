package com.bo.anim.customviewgroup.listview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bo.anim.R;

/**
 * Created by Administrator on 2017/11/5.
 */

public class MyListView extends ListView implements View.OnTouchListener, AbsListView.OnScrollListener {

    private static final String TAG = "listview----";

    //头部的View
    private View headerContent;

    //头部View中的子控件
    public ImageView img;

    public TextView tv;

    //测量后得到头部的高度
    public int measuerHeaderHeight;

    enum Signal {
        NONE, //初始状态
        PULL, //正在下拉的状态
        RELEASE, //提示松开刷新
        REFRESHING //正在刷新
    }

    Signal flag = Signal.NONE; //设置为初始状态

    private onRefreshListener refreshListener;

    public void setRefreshListener(onRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        if (headerContent == null) {


            headerContent = LayoutInflater.from(getContext()).inflate(R.layout.listview_header, null);

            img = ((ImageView) headerContent.findViewById(R.id.img));
            tv = ((TextView) headerContent.findViewById(R.id.tv1));

            measureView(headerContent);
            measuerHeaderHeight = headerContent.getMeasuredHeight();
            Log.i(TAG, "init: " + measuerHeaderHeight);
            addHeaderView(headerContent);
            headerContent.setPadding(0, -measuerHeaderHeight, 0, 0);
            headerContent.invalidate();
        }
        setOnTouchListener(this);
        setOnScrollListener(this);
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //得到子View的measureSpec
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int lpHeight = layoutParams.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


    //手指按下的时候的Y值
    private float mFirstY;

    private float mMoveY;

    //是否正在回弹
    private boolean isBack;

    //是否可以拖动
    private boolean isDrag;

    //头部View的paddingTop
    int paddingTop;

    //将下拉出来的速度放慢
    private static final float SLOWLY = 2f;

    boolean isRotate = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isBack) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        int action = event.getAction();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //判断是第一个手指的时候才执行这个方法
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    isDrag = true;
                }
                mFirstY = event.getRawY();
                paddingTop = headerContent.getPaddingTop();
                Log.i(TAG, "paddingTop:" + paddingTop);
                break;
            case MotionEvent.ACTION_MOVE:
                if (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1)
                    if (isDrag) {

                        mMoveY = event.getRawY() - mFirstY;  //这样算是手指按下去的点相对于移动的点的距离
                        mFirstY = event.getRawY();
                        // Log.i(TAG, "paddingTop: " + paddingTop + "mMoveY: " + mMoveY);
                        headerContent.setPadding(0, (int) (mMoveY / SLOWLY +
                                headerContent.getPaddingTop()), 0, 0);

                        //headerContent.getPaddingTop()初始值是负，所以要加上measuerHeaderHeight
                        //这样往下拉的过程中就可以得出拉出的高度
                        Log.i(TAG, "getPaddingTop(): " + (headerContent.getPaddingTop())
                                + "--" + (mMoveY) + "top" + event.getY());
                        int scroll = getScroll();
                        switch (flag) {
                            case NONE:
                                //刚进来判断是否滑动，如果是正在滑动，标记为下拉
                                if (headerContent.getPaddingTop() + measuerHeaderHeight > 0)
                                    flag = Signal.PULL;
                                break;
                            case PULL:
                                tv.setText("下拉刷新");
                                //这里最开始用 headerContent.getPaddingTop()>0 来判断，但是
                                //headerContent.getPaddingTop()不知道为什么这个值一直有差错。
                                if (scroll > measuerHeaderHeight) {
                                    if (!isRotate) {
                                        doRotateYAnim(0, 180);
                                        isRotate = true;
                                    }
                                    flag = Signal.RELEASE;
                                } else {
                                    if (isRotate) {
                                        doRotateYAnim(180, 360);
                                        isRotate = false;
                                    }
                                }
                                break;
                            case RELEASE:
                                tv.setText("放开刷新");
                                if (scroll < measuerHeaderHeight) {
                                    flag = Signal.PULL;
                                } else {
                                    flag = Signal.REFRESHING;
                                }
                                break;
                        }
                    }
                break;
            case MotionEvent.ACTION_UP:
                if (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1) {
                    if (flag == Signal.REFRESHING) {
                        doAnim1(headerContent.getPaddingTop());
                    } else if (flag == Signal.PULL) {
                        doAnim(headerContent.getPaddingTop());
                    }
                }
                //这里面传入getPaddingTop更加合适 传入paddingTop的话是按下的时候的paddingTop  没有及时更新。
                mFirstY = 0;
                mMoveY = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    isDrag = false;
                    Log.i(TAG, "第一根手指已经抬起");
                }
                //每当有手指抬起的时候就将移动的数值置为0，因为按下的时候可以得到paddingTop，手指没有全部抬起的
                //的时候是不会执行动画的。也就是paddingTop不是设置的值
                mMoveY = 0;
                mFirstY = event.getRawY();
                //抬起最后一根手指的前一个或者两个手指的时候如果将这个置为0，
                //那么MOVE中的event.getRawY()值就会是没有抬起的那根手指的值，这样就是造成瞬间
                //滑动很大的距离（event.getRawY() - 0 ）
                break;
        }
        return false;
    }

    private void doRotateYAnim(int fromDegree, int toDegree) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(img, "rotation", fromDegree, toDegree);
        animator.setDuration(200);
        animator.start();
    }

    private void doAnim1(final float distance) {
        Log.i(TAG, "measuerHeaderHeight: " + distance);
        ValueAnimator animator = ValueAnimator.ofFloat(distance, 0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1) {
                    Log.i(TAG, "animatedValue: " + animatedValue + "---");
                    if (getScroll() > measuerHeaderHeight) {
                        headerContent.setPadding(0, (int) animatedValue, 0, 0);
                        headerContent.invalidate();
                    }
                }
                // smoothScrollToPosition(0);
            }
        });
        //animator.setDuration((long) (distance / getHeight() * 300));
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "animatedValue: 动画开始");
                isBack = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "animatedValue: 动画结束");
                //isBack = false;
                if (refreshListener != null)
                    refreshListener.onRefresh();
                // headerContent.setPadding(0, -measuerHeaderHeight, 0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void doAnim(final float distance) {
        Log.i(TAG, "measuerHeaderHeight: " + distance);
        ValueAnimator animator = ValueAnimator.ofFloat(distance, -measuerHeaderHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1) {
                    Log.i(TAG, "animatedValue: " + animatedValue + "---");
                    if (getScroll() > 0) {
                        headerContent.setPadding(0, (int) animatedValue, 0, 0);
                        headerContent.invalidate();
                    }
                }
                // smoothScrollToPosition(0);
            }
        });
        //animator.setDuration((long) (distance / getHeight() * 300));
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "animatedValue: 动画开始");
                isBack = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "animatedValue: 动画结束");
                isBack = false;
                // headerContent.setPadding(0, -measuerHeaderHeight, 0, 0);
                //最后需要将旋转的图片位置复原
                doRotateYAnim(180, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    //
    public int getScroll() {
        View c = this.getChildAt(1);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = this.getFirstVisiblePosition();
        //System.out.println("-----------" + c + top);
        //return -top + firstVisiblePosition * c.getHeight() ;
        return c.getTop();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //System.out.println("listview--------------" + firstVisibleItem);
    }

    public void onFinish() {
        doAnim(headerContent.getPaddingTop());
        flag = Signal.NONE; //刷新结束将状态置为初始状态
        isRotate = false;
    }

    //父类中测量子View
    /*@Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {

            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                    mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin
                            + widthUsed, lp.width);
            final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                    mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin
                            + heightUsed, lp.height);

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

    }*/
}
