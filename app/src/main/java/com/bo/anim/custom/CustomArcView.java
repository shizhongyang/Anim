package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TT on 2017-10-17.
 */

public class CustomArcView extends View {

    private Paint paint;
    private double mRadius = 100d;
    private double radian = Math.PI / 36;

    private Path mPath;

    public CustomArcView(Context context) {
        this(context, null);
    }

    public CustomArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.BLUE);  //设置画笔颜色
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(2);//设置画笔宽度

        mPath = new Path();
        mPath.moveTo((float) mRadius, 0);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
   

        return super.onTouchEvent(event);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        RectF rect1 = new RectF(100, 0, 300, 200);
        canvas.drawArc(rect1, 0, 180, true, paint);

        RectF rect2 = new RectF(400, 0, 600, 200);
        canvas.drawArc(rect2, 0, 180, false, paint);

        RectF rect3 = new RectF(400, 0, 600, 210);
        canvas.drawArc(rect3, 0, 180, false, paint);

        double tan = Math.tan(radian);
        double cos = Math.cos(radian);
        double cos1 = Math.cos(radian * 2);


        double sin = Math.sin(radian);
        double sin1 = Math.sin(radian * 2);

        //第二个点的坐标
        double v = mRadius / cos;
        System.out.println(cos + "-" + v);
        double x = v * cos;
        double y = v * sin;


        double x1 = mRadius * cos1;
        double y1 = mRadius * sin1;

        mPath.quadTo((float) x, (float) y, (float) x1, (float) y1);

        System.out.println("-----" + tan + "-" + x + "-"
                + y+ "-" + x1 + "-" + y1);

        canvas.drawPath(mPath,paint);

    }
}
