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
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bo.anim.R;

/**
 * Created by Administrator on 2017/11/5.
 */

public class MyListView extends ListView implements View.OnTouchListener {

    private static final String TAG = "listview----";

    //头部的View
    public View headerContent;

    //头部View中的子控件
    public ImageView img;
    public TextView tv;

    //测量后得到头部的高度
    public int measuerHeaderHeight;


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
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //得到ziView的measureSpec
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

        Log.i(TAG, "正在回弹" + isBack);
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
                        mMoveY = event.getRawY() - mFirstY;
                        Log.i(TAG, "paddingTop: " + paddingTop + "mMoveY: " + mMoveY);
                        headerContent.setPadding(0, (int) (mMoveY / SLOWLY + paddingTop), 0, 0);
                        if (headerContent.getPaddingTop() > measuerHeaderHeight) {
                            if (!isRotate) {
                                doRotateYAnim(0, 180);
                                isRotate = true;
                            }
                        } else {
                            if (isRotate) {
                                doRotateYAnim(180, 360);
                                isRotate = false;
                            }
                        }
                        // Log.i(TAG, "mMoveY: " + mMoveY);
                    }
                break;
            case MotionEvent.ACTION_UP:
                if (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1)
                    doAnim(headerContent.getPaddingTop());
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

    private void doAnim(final float distance) {
        Log.i(TAG, "measuerHeaderHeight: " + measuerHeaderHeight);
        ValueAnimator animator = ValueAnimator.ofFloat(distance, -measuerHeaderHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                Log.i(TAG, "animatedValue: " + animatedValue);
                headerContent.setPadding(0, (int) animatedValue, 0, 0);
                headerContent.invalidate();
            }
        });
        //animator.setDuration((long) (distance / getHeight() * 300));
        animator.setDuration(300);

        animator.setInterpolator(new AccelerateInterpolator());
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
