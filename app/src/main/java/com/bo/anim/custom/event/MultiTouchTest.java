package com.bo.anim.custom.event;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TT on 2017-10-20.
 */

public class MultiTouchTest extends View{
    String TAG = "shi";

    //判读是否存在第二个手指
    boolean haveSecondPoint = false;

    //记录第二个手指的位置
    PointF point = new PointF(0,0);


    private Paint mDeafultPaint = new Paint();

    public MultiTouchTest(Context context) {
        this(context,null);
    }

    public MultiTouchTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setTextAlign(Paint.Align.CENTER);
        mDeafultPaint.setTextSize(30);
        mDeafultPaint.setColor(0xff000000);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionIndex = event.getActionIndex();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //判断是否有第二个手指
                if (event.getPointerId(actionIndex) == 1){
                    haveSecondPoint = true;
                    point.set(event.getX(),event.getY());
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(actionIndex) == 1){
                    haveSecondPoint = false;
                    point.set(0,0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (haveSecondPoint){
                    //通过Pointid来获取pointerIndex
                    int pointerIndex = event.findPointerIndex(1);
                    //通过pointerIndex来获取对应的坐标
                    point.set(event.getX(pointerIndex), event.getY(pointerIndex));
                    System.out.println("----"+point.x+point.y);
                }
                break;
        }

        invalidate();
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth()/2, getHeight()/2);
        canvas.drawText("追踪第2个按下手指的位置", 0, 0, mDeafultPaint);
        canvas.restore();

        // 如果屏幕上有第2个手指则绘制出来其位置
        if (haveSecondPoint) {
            canvas.drawCircle(point.x, point.y, 50, mDeafultPaint);
        }
    }
}
