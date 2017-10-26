package com.bo.anim.custom.event;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by TT on 2017-10-23.
 * {}
 */

public class MyZoomImageView extends ImageView implements
        ViewTreeObserver.OnGlobalLayoutListener,
        View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "shi--------------";

    /**
     * 默认缩放
     */
    private float mInitScale = 1.0f;
    /**
     * 双击
     */
    private float mMidScale = 2.0f;

    /**
     * 最大放大倍数
     */
    public static final float mMaxScale = 4.0f;


    //检测手势伸缩的类 独立的类不是GestureDetector的子类
    private ScaleGestureDetector mScaleGestureDetector = null;

    /**
     * 检测类似长按啊 轻按啊 拖动 快速滑动 双击啊等等
     */
    private GestureDetector mGestureDetector = null;

    //这个标记用于判断是否正在自动伸缩，防止多次出发双击事件。
    private boolean mIsAutoScaling;
    /**
     * Matrix的对图像的处理
     * Translate 平移变换
     * Rotate 旋转变换
     * Scale 缩放变换
     * Skew 错切变换
     * 这个初始化的是一个单位矩阵
     * 1
     * 1
     * 1
     */
    private Matrix mScaleMatrix = new Matrix();

    /**
     * 处理矩阵的9个值
     */
    private float[] mMartixValue = new float[9];

    private int mTouchSlop;


    public MyZoomImageView(Context context) {
        this(context, null);
    }

    public MyZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);
        this.setOnTouchListener(this);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        //监听双击事件 SimpleOnGestureListener是OnGestureListener接口实现类,
        //使用这个复写需要的方法就可以不用复写所有的方法
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                System.out.println(TAG + "开始执行");
                if (mIsAutoScaling) {  //如果正在执行缩放就不继续下去
                    return true;
                }
                //缩放的中心点
                float x = e.getX();
                float y = e.getY();
                System.out.println(TAG + "x：" + x + "y：" + y);

                //如果当前的缩放值小于这个临界值，则进行放大
                if (getScale() < mMidScale) {
                    mIsAutoScaling = true;
                    //view中的方法 已x,y为坐标点放大到mMidScale 延时10ms
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                } else {
                    //如果当前缩放值大于这个临界值 则进行缩小操作 缩小到mInitScale
                    mIsAutoScaling = true;
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                }

                return true;
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    private boolean once = true;

    //初始化图片的大小，必须在onAttachedToWindow以后才能得到大小
    @Override
    public void onGlobalLayout() {
        if (!once)
            return;
        //得到ImageView的Drawable
        Drawable drawable = getDrawable();
        if (drawable == null)
            return;
        int width = getWidth();
        int height = getHeight();  //得到View的宽高

        int imgWidth = drawable.getIntrinsicWidth();
        int imgHeight = drawable.getIntrinsicHeight(); //得到图片的宽高

        float scale = 1.0f;
        if (imgWidth > width && imgHeight <= height)
            scale = (float) width / imgWidth;
        if (imgHeight > height && imgWidth <= width) {
            scale = (float) height / imgHeight;
        }
        if (imgWidth > width && imgHeight > height) {
            //如果图片的宽和高都大于控件宽高，取缩放值小的那个
            scale = Math.min((float) width / imgWidth, (float) height / imgHeight);
        }
        mInitScale = scale;  //设置缩放的比例

        //将图片移动到屏幕的中心
        mScaleMatrix.preTranslate(width / 2 - imgWidth / 2, height / 2 - imgHeight / 2);
        mScaleMatrix.postScale(scale, scale, width / 2, height / 2);
        setImageMatrix(mScaleMatrix);
        once = false;
    }

    //获得缩放的比例
    private float getScale() {
        mScaleMatrix.getValues(mMartixValue);
        return mMartixValue[Matrix.MSCALE_X];
    }


    //-----------------------------------------------------------------------------------------
    /**
     * 处理现图片放大后移动查看
     */
    private int mLastPointCount;//触摸点发生移动时的触摸点个数
    private boolean isDrag; //判断是否可以拖拽
    private float mLastX;
    private float mLastY;
    private boolean isCheckTopAndBottom;//是否可以上下拖动
    private boolean isCheckLeftAndRight;//是否可以左右拖动

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        //可能出现多手指触摸的情况 ACTION_DOWN事件只能执行一次所以多点触控不能在down事件里面处理
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;
        //每当触摸点发生移动时(从静止到移动)，重置mLasX , mLastY mLastPointCount防止再次进入
        if (mLastPointCount != pointerCount) {
            isDrag = false;
            mLastX = x;
            mLastY = y;
        }
        //重新赋值 说明如果是一些列连续滑动的操作就不会再次进入上面的判断 否则会重新确定坐标移动原点
        mLastPointCount = pointerCount;
        RectF matrixRectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (matrixRectF.width() > getWidth() || matrixRectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //按下的时候如果发现图片缩放宽或者高大于屏幕宽高则请求viewpager不拦截事件交给ZoomImageView处理
                //ZoomImageView可以进行缩放操作
                if (matrixRectF.width() > getWidth() || matrixRectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isDrag) {
                    isDrag = isMoveAction(dx, dy);
                    Log.e(TAG, "移动3---->" + pointerCount);
                }
                if (isDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (matrixRectF.width() < getWidth()) {//如果图片宽度小于控件宽度
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (matrixRectF.height() < getHeight()) {//如果图片的高度小于控件的高度
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        System.out.println("img------------"+dy);
                        mScaleMatrix.postTranslate(dx, dy);
                        //解决拖拽的时候左右 上下都会出现留白的情况
                        checkBorderAndCenterWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointCount = 0;
                break;
        }
        return true;
    }

    private void checkBorderAndCenterWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);//处理偏移量
    }


    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        Log.e(TAG, "matrix scale---->" + scale);
        float scaleFactor = detector.getScaleFactor();
        Log.e(TAG, "scaleFactor---->" + scaleFactor);
        if (getDrawable() == null) {
            return true;
        }
        //判断是否满足条件 满足条件再进行放大和缩小
        if ((scale < mMaxScale && scaleFactor > 1.0f)  //放大
                || (scale > mInitScale && scaleFactor < 1.0f)) //缩小
        {
            if (scaleFactor * scale < mInitScale) {
                scaleFactor = mInitScale / scale;
                Log.e(TAG, "进来了1" + scaleFactor);
            }
            if (scaleFactor * scale > mMaxScale) {
                scaleFactor = mMaxScale / scale;
                Log.e(TAG, "进来了2---->" + scaleFactor);
            }

            Log.e(TAG, "scaleFactor2---->" + scaleFactor);
            //设置缩放比例
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());//缩放中心是两手指之间
            checkBorderAndCenterWhenScale();//解决这种缩放导致缩放到最小时图片位置可能发生了变化

            //            mScaleMatrix.postScale(scaleFactor, scaleFactor,
            //                    getWidth() / 2, getHeight() / 2);//缩放中心是屏幕中心点
            setImageMatrix(mScaleMatrix);//通过手势给图片设置缩放
        }
        //返回值代表本次缩放事件是否已被处理。如果已被处理，那么detector就会重置缩放事件；
        // 如果未被处理，detector会继续进行计算，修改getScaleFactor()的返回值，直到被处理为止。
        // 因此，它常用在判断只有缩放值达到一定数值时才进行缩放
        return true;
    }

    //这个里面必须返回true
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    private class AutoScaleRunnable implements Runnable {
        private float mTargetScale;//缩放的目标值
        private float x; //缩放中心点
        private float y;
        private float tempScale;//可能是BIGGER可能是SMALLER
        private float BIGGER = 1.07f;
        private float SMALLER = 0.93f;

        AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tempScale = BIGGER;
            }
            if (getScale() > mTargetScale) {//双击缩小
                //这个缩放比1f小就行 随便取个0.93
                tempScale = SMALLER;
            }
        }

        @Override
        public void run() {
            //执行缩放
            mScaleMatrix.postScale(tempScale, tempScale, x, y);
            //在缩放的时候解决上下留白的情况
            //......
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            float currentScale = getScale();
            //如果当前正在放大操作并且当前的放大尺度小于缩放的目标值,或者正在缩小并且缩小的尺度大于目标值
            //则再次延时16ms递归调用直到缩放到目标值
            if ((tempScale > 1.0f && currentScale < mTargetScale)
                    || (tempScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                //代码走到这儿来说明不能再进行缩放了，可能放大的尺寸超过了mTrgetScale，
                //也可能缩小的尺寸小于mTrgetScale
                //所以这里我们mTrgetScale / currentScale 用目标缩放尺寸除以当前的缩放尺寸
                //得到缩放比，重新执行缩放到
                //mMidScale或者mInitScale
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                //在伸缩的时候解决上下留白的情况
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                System.out.println(TAG + getScale() + scale);
                mIsAutoScaling = false;
            }
        }
    }

    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() > width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
                Log.e(TAG, "宽有问题1---->" + rectF.width() + "--" + rectF.left + "--" + width);
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
                Log.e(TAG, "宽有问题2---->" + rectF.width() + "--" + rectF.left + "--" + width);
            }
        }

        if (rectF.height() > height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        // 如果宽或高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + rectF.width() * 0.5f;
            Log.e(TAG, "宽有问题3---->" + rectF.width() + "--" + rectF.right + "结果" + deltaX);
        }

        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height();
            Log.e(TAG, "高有问题4---->" + rectF.height() + "--" + rectF.bottom + "结果" + deltaY);
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 获得图片放大缩小以后的宽和高，以及l,r,t,b
     */
    private RectF getMatrixRectF() {
        Matrix rMatrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            //使这个矩形的宽和高同当前图片一致
            //设置坐标位置(l和r是左边矩形的坐标点 tb是右边矩形的坐标点 lr设置为0就是设置为原宽高)
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            //将矩阵映射到矩形上面，之后我们可以通过获取到矩阵的上下左右坐标以及宽高
            //来得到缩放后图片的上下左右坐标和宽高
            rMatrix.mapRect(rectF);
        }
        return rectF;
    }
}
