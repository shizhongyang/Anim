package com.bo.anim.custom;


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

public class PathView extends View {

    private Paint mPaint;
    private Path path;

    private float pos[] = new float[2];

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        path = new Path();
        path.moveTo(223, 213);
        path.quadTo(59, 182, 213, 341);
      //  path.quadTo(400,400,500,300);
        path.quadTo(213-59+213,182,223,213);
        final PathMeasure measure = new PathMeasure(path,true);
        float length = measure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(0,length);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private boolean posTan;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                posTan = measure.getPosTan(animatedValue,pos,null);
                invalidate();
            }
        });

        animator.setDuration(3000);
        animator.start();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println(pos[0]+pos[1]);
        canvas.drawCircle(pos[0],pos[1],30,mPaint);
        canvas.drawPath(path,mPaint);

    }
}
