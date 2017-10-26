package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-26.
 */

public class CameraTestView extends View {
    private Camera camera;
    private Bitmap bitmap;
    private Paint paint;


    public CameraTestView(Context context) {
        this(context,null);
    }

    public CameraTestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        camera = new Camera();
        //将要展示的图片解析成bitmap
        bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.google_map);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        camera.save();
        camera.setLocation(0,0,-6);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap,30,30,paint);
        canvas.restore();


        canvas.save();
        camera.save();
        camera.setLocation(0,0,-4);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap,30,30,paint);
        canvas.restore();


        canvas.save();
        camera.save();
        camera.setLocation(0,0,-2);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap,30,30,paint);
        canvas.restore();

        canvas.save();
        camera.save();
        camera.setLocation(0,0,0);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.drawBitmap(bitmap,30,30,paint);
        canvas.restore();
    }
}
