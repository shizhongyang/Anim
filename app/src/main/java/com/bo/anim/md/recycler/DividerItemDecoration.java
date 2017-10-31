package com.bo.anim.md.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-30.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint dividerPaint;
    private int orientation = LinearLayout.VERTICAL;

    public DividerItemDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayout.HORIZONTAL && orientation != LinearLayout.VERTICAL) {
            throw new IllegalArgumentException("不合法");
        }
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //1.调用此方法（首先会先获取条目之间的间隙宽度---Rect矩形区域）
        //获得条目的偏移量（所有条目都回调用一次该方法）
        if (orientation == LinearLayout.VERTICAL)
            outRect.bottom = 5;
        else
            outRect.right = 5;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        /*
            RecyclerView.getChildCount()方法的返回值并不是recyclerView的Adapter中所有的item的数量，
            而是当前屏幕中出现在RecyclerView中item的数量，
            一个item只要露出一点点，就算出现，就会被包含在内。
        * */
        if (orientation == LinearLayout.VERTICAL) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                float top = view.getBottom();
                float bottom = view.getBottom() + 5;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        } else {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - top;
            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                int left = view.getRight();
                int right = left + 5;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }
}
