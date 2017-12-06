package com.bo.anim.custom.zhihu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by TT on 2017-12-06.
 */

public class AdImageView extends AppCompatImageView {
    public AdImageView(Context context) {
        this(context, null);
    }

    public AdImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h; //控件的高度

    }

    private int mDx;
    private int mMinDx;

    public void setDx(int dx) {
        if (getDrawable() == null) {
            return;
        }
        mDx = dx - mMinDx;
        if (mDx <= 0) {
            mDx = 0;
        }

        if (mDx > getDrawable().getBounds().height() - mMinDx)
            mDx = getDrawable().getBounds().height() - mMinDx;

        invalidate();
    }

    public int getDx() {
        return mDx;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        Log.i("imageView", "onDraw: h" + h);
        drawable.setBounds(0, 0, w, h);
        //drawable.draw(canvas);
        canvas.save();
        canvas.translate(0, -getDx());
        super.onDraw(canvas);
        canvas.restore();

    }
}
