package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TT on 2017-10-10.
 */

public class MyPointView1 extends View {

    private Point mPoint = new Point(100);

    public MyPointView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPoint != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(100, 100, mPoint.getRadius(), paint);
        }
        super.onDraw(canvas);
    }

    public int getPointRadius() {
        return 50;
    }


/*    第一点，这个set函数所对应的属性应该是pointRadius或者PointRadius。
前面我们已经讲了第一个字母大小写无所谓，后面的字母必须保持与set函数完全一致。
    第二点，在setPointRadius中，先将当前动画传过来的值保存到mPoint中，
    做为当前圆形的半径。然后强制界面刷新*/

    void setPointRadius(int radius) {
        mPoint.setRadius(radius);
        invalidate();
    }


    public class Point {
        private int mRadius;

        public Point(int radius) {
            mRadius = radius;
        }

        public int getRadius() {
            return mRadius;
        }

        public void setRadius(int radius) {
            mRadius = radius;
        }
    }
}
