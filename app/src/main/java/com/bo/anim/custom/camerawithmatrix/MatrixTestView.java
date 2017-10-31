package com.bo.anim.custom.camerawithmatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.bo.anim.R;


/**
 * Created by TT on 2017-10-26.
 */

public class MatrixTestView extends View {

    private Paint paint;
    private Matrix matrix;
    private Matrix matrix1;
    private Matrix matrix2;

    public MatrixTestView(Context context) {
        this(context, null);
    }

    public MatrixTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Bitmap bitmap;

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.google_map);
        //画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FF4081"));
        //矩阵
        matrix = new Matrix();
        matrix1 = new Matrix();
        matrix2 = new Matrix();

        //1.matrix的移动
        matrix.setTranslate(100f, 100f);
        matrix1.setTranslate(100f, 100f);
        matrix2.setTranslate(100f, 100f);

        //2.旋转  如果只有一个操作那么这些结果是一样的，如果前面加上了移动，那么调用下面这些方法
        //得到的结果就不一样了，因为矩阵不满足乘法交换率，前乘和后乘得到的值不一样，
        //前乘和后乘是针对于前面移动的那个矩阵的位置。

        matrix.setRotate(30); //围绕中心旋转
        matrix1.preRotate(30); //前乘
        matrix2.postRotate(30); //后乘
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate((getWidth()-bitmap.getWidth())/2,(getHeight()-bitmap.getHeight())/2);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.restore();

        canvas.save();
        canvas.translate((getWidth()-bitmap.getWidth())/2,(getHeight()-bitmap.getHeight())/2);
        canvas.drawBitmap(bitmap,matrix,paint);
        canvas.restore();

        canvas.save();
        canvas.translate((getWidth()-bitmap.getWidth())/2,(getHeight()-bitmap.getHeight())/2);
        canvas.drawBitmap(bitmap,matrix1,paint);
        canvas.restore();

        canvas.save();
        canvas.translate((getWidth()-bitmap.getWidth())/2,(getHeight()-bitmap.getHeight())/2);
        canvas.drawBitmap(bitmap,matrix2,paint);
        canvas.restore();
    }
}
















