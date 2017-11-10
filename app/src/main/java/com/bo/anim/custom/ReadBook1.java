package com.bo.anim.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TT on 2017-10-16.
 */

public class ReadBook1 extends View {
    /**
     * 中心圆的画笔
     */
    private Paint mPaint;

    /**
     * 渐变圆的画笔
     */
    private Paint mPaint1;

    /**
     * 路径的画笔
     */
    private Paint mPaint3;

    private Paint mPaintText;

    private int mWidth;
    private int mHeight;
    private int mRadius = 10;

    private Path mPath;
    private Path mPath1;

    private ValueAnimator animatorCircle;

    private PathMeasure measure;

    private Float animatedValue = 0f;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //判断是展开是回收
    private boolean flag;

    public ReadBook1(Context context) {
        this(context, null);
    }

    public ReadBook1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadBook1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(Color.WHITE);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.WHITE);
        mPaint3.setStrokeWidth(4);


        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAlpha(0);
        mPaintText.setTextAlign(Paint.Align.CENTER);


        mPath = new Path();
        mPath1 = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("调用次数");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mPath.moveTo(mWidth / 2, mHeight / 2);
        mPath.lineTo(mWidth / 2 + 100, mHeight / 2 - 100);
        mPath.lineTo(mWidth / 2 + 300, mHeight / 2 - 100);

        pathAnimStartOrStop();

    }

    public void pathAnimStartOrStop() {
        measure = new PathMeasure(mPath, false);
        float length = measure.getLength();
        ValueAnimator animator;
        if (flag) {
            animator = ValueAnimator.ofFloat(length, 0);
        } else
            animator = ValueAnimator.ofFloat(0, length);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (Float) animation.getAnimatedValue();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画展开式时候
                if (!flag) {
                    mPaint.setAlpha(255);
                    mPaint1.setAlpha(255);
                    startAnim();
                    System.out.println("动画展开的时候");
                }
                ReadBook1.this.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (flag) {
                    mPaintText.setAlpha(0);
                    mPaint.setAlpha(0);
                    mPaint1.setAlpha(0);
                    System.out.println("动画关闭的时候");
                    animatorCircle.cancel();
                } else {
                    System.out.println("动画展开的时候");
                    mPaintText.setAlpha(255);
                }
                flag = !flag;
                ReadBook1.this.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setDuration(3000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 10, mPaint1);

        //canvas.drawPath(mPath, mPaint2);

        mPath1.reset();
        // 硬件加速的BUG
        mPath1.lineTo(0, 0);
        float stop = animatedValue;
        float start = 0;
        measure.getSegment(start, stop, mPath1, true);
        canvas.drawPath(mPath1, mPaint3);

        canvas.drawText("Hello World", mWidth / 2 + 200, mHeight / 2 - 104, mPaintText);
    }

    public void startAnim() {
        animatorCircle = ValueAnimator.ofFloat(10, 50);
        final float per = 255f / 40;
        animatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mRadius = (int) animatedValue;
                int a = (int) ((40 - (animatedValue - 10)) * per);
                System.out.println(a);
                mPaint.setAlpha(a);
                invalidate();
            }
        });
        animatorCircle.setRepeatMode(ValueAnimator.RESTART);
        animatorCircle.setRepeatCount(ValueAnimator.INFINITE);
        animatorCircle.setDuration(3000);
        animatorCircle.start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animatorCircle != null)
            animatorCircle.cancel();
    }

}
