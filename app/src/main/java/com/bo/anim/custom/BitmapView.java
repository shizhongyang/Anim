package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-17.
 */

public class BitmapView extends View {

    private Bitmap bmp;

    private float x1;
    private float y1;

    //中心点的坐标
    private float centerX = 0;
    private float centerY = 0;

    private VelocityTracker mVelocityTracker = null;
    private long mDownTime;
    private float disPerSecond;
    private boolean isFling;

    public BitmapView(Context context) {
        this(context, null);
    }

    public BitmapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //方法二：从图片中加载
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, null);
        init();
    }

    private Paint mPaint;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
    }

    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;

    private boolean canDrag = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        // System.out.println("-------------");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //设置当手指为第一个时候才可以滑动。
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    canDrag = true;
                    mDownTime = System.currentTimeMillis();
                    System.out.println("按下了:");
                    mLastX = x;
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    //  System.out.println("移动:");
                    //计算出移动的距离
                    x1 = x - mLastX;
                    y1 = y - mLastY;
                    invalidate();
                    /*mLastX = x;
                    mLastY = y;*/
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                //手指的id不会变，当抬起的是第一个手指的时候将可以滑动设置为false 这样就不可以滑动了。
                if (event.getPointerId(event.getActionIndex()) == 0) {
                    canDrag = false;
                    System.out.println("抬起:");
                    centerX = centerX + x1;
                    centerY = centerY + y1;
                    disPerSecond = y1 * 1000 /
                            (System.currentTimeMillis() - mDownTime);

                    System.out.println("----" + disPerSecond);
                    x1 = 0;
                    y1 = 0;

                    //1000是1000毫秒的意思，即获取每秒的速率
                    mVelocityTracker.computeCurrentVelocity(10);
                    Log.i("xz", "ACTION_UP");
                    //0是初始数据
                    Log.i("xz", mVelocityTracker.getXVelocity(0) + "");
                    Log.i("xz", mVelocityTracker.getYVelocity(0) + "");

                    post(new Task());
                }
                break;
        }
        System.out.println("--------x1:" + x1 + "y1:" + y1);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*canvas.save();

        canvas.drawBitmap(bmp,0,0,null);
        canvas.restore();*/
        /* mPaint.setShader(new LinearGradient(getWidth() / 2, getHeight() / 2 - 100, getWidth() / 2,
                getHeight() / 2 - 100, 0x00ff0000, 0xffff0000, Shader.TileMode.CLAMP));*/
        // canvas.drawLine(0,0,getWidth(),getHeight(),mPaint);
        canvas.drawCircle(centerX + x1, centerY + y1, 100, mPaint);
    }

    class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("执行时间：" + System.currentTimeMillis());
            if (Thread.interrupted()) {
                return;
            }
            // 如果小于20,则停止
            if ((int) Math.abs(disPerSecond) < 800) {
                isFling = false;
                return;
            }
            isFling = true;
            centerY += (disPerSecond / 50);
            disPerSecond /= 1.0666F;
            postDelayed(this, 30);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
