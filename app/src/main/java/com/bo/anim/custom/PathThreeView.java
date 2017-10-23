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

public class PathThreeView extends View {

    private Paint mPaint;
    private Path path;
    private Path path1;
    private Path path2;
    private Path path3;
    private Path path4;

    private float pos[] = new float[2];

    public PathThreeView(Context context) {
        this(context,null);
    }

    public PathThreeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathThreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        path = new Path();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        path4 = new Path();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        path.moveTo(207, 0);
        path1.moveTo(207, 0);
        path2.moveTo(207, 0);
        path3.moveTo(207, 0);
        path4.moveTo(207, 0);

        path.cubicTo(150, 100, 232, 161, 117, 497);
        path1.cubicTo(150, 100, 232, 161, 295, 497);
        path2.cubicTo(150, 100, 232, 161, 66, 497);
        path3.cubicTo(150, 100, 232, 161, 200, 497);
        path4.cubicTo(150, 100, 232, 161, 500, 497);



        measurePath(3000,path);
    }

    private void measurePath(int time,Path path) {
        final PathMeasure measure = new PathMeasure(path,true);
        float length = measure.getLength();


        ValueAnimator animator = ValueAnimator.ofFloat(0,length);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                measure.getPosTan(animatedValue,pos,null);
                invalidate();
            }
        });

        animator.setDuration(time);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println(pos[0]+pos[1]);
        canvas.drawCircle(pos[0],pos[1],30,mPaint);
        canvas.drawPath(path,mPaint);
        canvas.drawPath(path1,mPaint);
        canvas.drawPath(path2,mPaint);
        canvas.drawPath(path3,mPaint);
        canvas.drawPath(path4,mPaint);
    }
}
