package com.bo.anim.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.bo.anim.R;


/**
 * Created by TT on 2017-10-18.
 */

public class CameraAndMatrixView extends View {


    private final Bitmap after;
    private final Bitmap bitmap;
    private Camera camera;
    private Matrix matrix;
    private Paint paint;
    private Canvas canvas;
    private Matrix matrix1;

    public CameraAndMatrixView(Context context) {
        this(context, null);
    }

    public CameraAndMatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraAndMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        camera = new Camera();
        matrix = new Matrix();

        setBackgroundColor(Color.parseColor("#3f51b5"));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ff4081"));

        //将要展示的图片解析成bitmap
        after = BitmapFactory.decodeResource(getResources(),
                R.mipmap.google_map);
        //得到一个新的bitmap，它的大小和要展示的图片一样
        bitmap = Bitmap.createBitmap(after.getWidth(), after.getHeight(),
                after.getConfig());

        //canvas = new Canvas(bitmap);
        matrix1 = new Matrix();
        matrix1.setScale(5f, 5, 50, 50);

    }

    /*
    *   Camera() 创建一个没有任何转换效果的新的Camera实例
        applyToCanvas(Canvas canvas) 根据当前的变换计算出相应的矩阵，然后应用到制定的画布上
        getLocationX() 获取Camera的x坐标
        getLocationY() 获取Camera的y坐标
        getLocationZ() 获取Camera的z坐标
        getMatrix(Matrixmatrix) 获取转换效果后的Matrix对象
        restore() 恢复保存的状态
        rotate(float x, float y, float z) 沿X、Y、Z坐标进行旋转
        rotateX(float deg)
        rotateY(float deg)
        rotateZ(float deg)
        save() 保存状态
        setLocation(float x, float y, float z)
        translate(float x, float y, float z)沿X、Y、Z轴进行平移
       * */


    /*  setTranslate(floatdx,floatdy)：控制Matrix进行平移
        setSkew(floatkx,floatky,floatpx,floatpy)：控制Matrix以px,py为轴心进行倾斜，kx,ky为X,Y方向上的倾斜距离
        setRotate(floatdegress)：控制Matrix进行旋转，degress控制旋转的角度
        setRorate(floatdegress,floatpx,floatpy)：设置以px,py为轴心进行旋转，degress控制旋转角度
        setScale(floatsx,floatsy)：设置Matrix进行缩放，sx,sy控制X,Y方向上的缩放比例
        setScale(floatsx,floatsy,floatpx,floatpy)：设置Matrix以px,py为轴心进行缩放，sx,sy控制X,Y方向上的缩放比例
*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bitmapWidth = after.getWidth();
        int bitmapHeight = after.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;
        //canvas.drawCircle(60, 60, 60, paint);

        //paint.setAlpha(100);

        /*matrix.reset();
        camera.save();
        camera.translate(10,-100,-180);
        camera.getMatrix(matrix);
        matrix.setScale(2,2);
        camera.restore();
        canvas.concat(matrix);*/

        //canvas.drawCircle(60, 60, 60, paint);

        // 把要展示的图片画在canvas上，此时bitmap上就有了要展示的图片
        //canvas.drawBitmap(after, matrix1, paint);

        canvas.save();
        camera.save();
        canvas.translate(getWidth() / 2, getHeight() / 2); //移动到中心
        camera.rotateX(-10);
        camera.rotateY(-10);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        canvas.drawBitmap(after,x,y,paint);
        canvas.restore();


        canvas.save();
        camera.save();
        camera.rotateX(20);
        camera.rotateY(20);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-getWidth() / 2, -getHeight() / 2); //前乘在旋转之前，移动到中心的位置
        matrix.postTranslate(getWidth() / 2, getHeight() / 2);  //后乘 旋转以后，移动到远点

        canvas.concat(matrix);
        canvas.drawBitmap(after,x,y,paint);
        canvas.restore();

    }
}
