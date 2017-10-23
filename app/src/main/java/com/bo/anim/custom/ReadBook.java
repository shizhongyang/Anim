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

public class ReadBook extends View {
    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;


    private Paint mPaintText;

    private int mWidth;
    private int mHeight;
    private int mRadius = 10;
    private float pos[] = new float[2];
    private float pos1[] = new float[2];

    private Path mPath;
    private Path mPath1;
    private Path mPath2;
    private Path mPath3;

    private boolean isCncel;
    private ValueAnimator animatorCircle;

    public ReadBook(Context context) {
        this(context, null);
    }

    public ReadBook(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadBook(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(Color.WHITE);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setColor(Color.WHITE);
        mPaint2.setAlpha(0);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.WHITE);
        mPaint3.setStrokeWidth(4);

        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint4.setStyle(Paint.Style.STROKE);
        mPaint4.setColor(0xffFF4081);
        mPaint4.setAlpha(0);
        mPaint4.setStrokeWidth(5);


        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAlpha(0);
        mPaintText.setTextAlign(Paint.Align.CENTER);


        mPath = new Path();
        mPath1 = new Path();
        mPath2 = new Path();
        mPath3 = new Path();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        startAnim();
        mPath1.moveTo(mWidth / 2, mHeight / 2);
        mPath1.lineTo(mWidth / 2 + 100, mHeight / 2 - 100);

        mPath.moveTo(mWidth / 2, mHeight / 2);
        mPath.lineTo(mWidth / 2 + 100, mHeight / 2 - 100);
        mPath.lineTo(mWidth / 2 + 300, mHeight / 2 - 100);

        //---------------------------------------------------------------
        mPath3.moveTo(mWidth / 2 + 300, mHeight / 2 - 100);
        mPath3.lineTo(mWidth / 2 + 100, mHeight / 2 - 100);

        mPath2.moveTo(mWidth / 2 + 300, mHeight / 2 - 100);
        mPath2.lineTo(mWidth / 2 + 100, mHeight / 2 - 100);
        mPath2.lineTo(mWidth / 2, mHeight / 2);


        final PathMeasure measure = new PathMeasure(mPath, false);
        float length = measure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(length, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                measure.getPosTan((Float) animation.getAnimatedValue(), pos, null);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                mPaintText.setAlpha(255);
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
        mPath1.lineTo(pos[0], pos[1]);
        //canvas.drawPath(mPath, mPaint2);

        canvas.drawPath(mPath1, mPaint3);

        canvas.drawText("Hello World", mWidth / 2 + 200, mHeight / 2 - 104, mPaintText);

        if (isCncel){
            mPath3.lineTo(pos1[0],pos1[1]);
            canvas.drawPath(mPath3, mPaint4);
        }
    }

    public void startAnim() {
        animatorCircle = ValueAnimator.ofFloat(10, 50);
        final float per = 255f / 40;
        animatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private boolean posTan;

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
    }


    public void cancelAnim(){
        isCncel = true;
        final PathMeasure measure = new PathMeasure(mPath2, false);
        float length = measure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(0, length);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                measure.getPosTan((Float) animation.getAnimatedValue(), pos1, null);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                mPaintText.setAlpha(0);


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorCircle.cancel();
                mPaint1.setAlpha(0);
                mPaint.setAlpha(0);
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
}
