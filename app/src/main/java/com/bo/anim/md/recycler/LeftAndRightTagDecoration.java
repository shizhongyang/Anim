package com.bo.anim.md.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-30.
 */

public class LeftAndRightTagDecoration extends RecyclerView.ItemDecoration {

    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;

    public LeftAndRightTagDecoration(Context context) {

        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        tagWidth = context.getResources().getDimensionPixelSize(R.dimen.tag_width);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            boolean isLeft = pos % 2 == 0;
            if (isLeft) {
                float left = child.getLeft();
                float right = left + tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();

                c.drawRect(left, top, right, bottom, leftPaint);
            } else {
                float right = child.getRight();
                float left = right - tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();

                c.drawRect(left, top, right, bottom, rightPaint);
            }

        }
    }
}
