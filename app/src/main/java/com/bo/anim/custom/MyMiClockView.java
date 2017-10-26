package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TT on 2017-10-26.
 */

public class MyMiClockView extends View {
    private Paint mScaleArcPaint;
    private Matrix mGradientMatrix = new Matrix();
    /* 刻度圆弧的外接矩形 */
    private RectF mScaleArcRectF;

    public MyMiClockView(Context context) {
        this(context, null);
    }

    public MyMiClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyMiClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mScaleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleArcPaint.setStyle(Paint.Style.STROKE);
        mScaleArcPaint.setStrokeWidth(20);
        mScaleArcRectF = new RectF();


        mScaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePaint.setStyle(Paint.Style.STROKE);
        int mBackgroundColor =  Color.parseColor("#3F51B5");
        mScaleLinePaint.setColor(mBackgroundColor);
        mScaleLinePaint.setStrokeWidth(6);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mDarkColor = Color.parseColor("#80ffffff");
        int mLightColor = Color.parseColor("#ffffff");
        SweepGradient mSweepGradient = new SweepGradient(w/2, h/2,
                new int[]{mDarkColor, mLightColor}, new float[]{0.75f, 1});

        mScaleArcRectF.set(w/2-300, h/2-300, w/2+300, h/2+300);

        mGradientMatrix.setRotate(- 90, w/2, h/2);
        mSweepGradient.setLocalMatrix(mGradientMatrix);
        mScaleArcPaint.setShader(mSweepGradient);
    }
    /* 刻度线画笔 */
    private Paint mScaleLinePaint;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mScaleArcRectF, 0, 360, false, mScaleArcPaint);
        for (int i = 0; i < 200; i++) {
            canvas.drawLine(getWidth() / 2,getHeight()/2-288,
                    getWidth() / 2, getHeight()/2-312, mScaleLinePaint);
            canvas.rotate(1.8f, getWidth() / 2, getHeight() / 2);
        }

    }
}
