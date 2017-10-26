package com.bo.anim.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-25.
 */

public class CameraView extends View {
    private final Bitmap after;
    private final Camera camera;
    private final Paint paint;
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 270);

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //将要展示的图片解析成bitmap
        after = BitmapFactory.decodeResource(getResources(),
                R.mipmap.google_map);

        camera = new Camera();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
       // animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.setStartDelay(1000);
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bitmapWidth = after.getWidth();
        int bitmapHeight = after.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;


        //统一坐标系
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degree);
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degree);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(after, x, y, paint);
        canvas.restore();


        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degree);
       // if (degree < 90)
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        /*else
            canvas.clipRect(0, 0, getWidth(), centerY);*/
        camera.rotateY(60);
        camera.applyToCanvas(canvas);
        canvas.rotate(degree);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(after, x, y, paint);
        canvas.restore();
        //System.out.println("-----------"+degree);
    }

}
