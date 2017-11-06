package com.bo.anim.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TT on 2017-10-19.
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置了精确的宽度得到的就是宽度，设置了wrap_content 还是得到的是屏幕的宽度
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        // wrap_content
        int width = 0;
        int height = 0;

        //记录每一行的宽和高
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();

        System.out.println("-----" + childCount);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                if (i == childCount - 1) {
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
                continue;
            }


            //resolveSizeAndState()
            /*
            测量子View  这里面传入的参数是子view和父view的measureSpec,传入的不是mode
             */
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到子view的margin
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //得到子view的宽度和高度
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            System.out.println("----------" + childWidth + childHeight + "-" + child.getMeasuredWidth());
            //一行测量大于整个容器的宽度
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {

                //记录下这一行的宽度
                width = Math.max(width, lineWidth);
                //重置下一行的宽度为第一个子View的宽度
                lineWidth = childWidth;

                //计算整个高度
                height += lineHeight;
                //重置下一行的高度为第一个子View的高度
                lineHeight = childHeight;


                System.out.println(lineWidth + lineHeight);
            } else {
                //这里面首先进入，如果这个宽度不大于整个屏幕的宽度，就不会走上面的方法
                //这时候还没有得到测量出来的宽度和高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }


            //接上面的条件，测量最后一个的时候，得到测量的宽度和高度。
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }

        }

        System.out.println("-----" + width + height);
        //如果模式是准确值，则设置准确的数值
        //如果不是，则设置测量以后的宽高
        setMeasuredDimension(
                (widthMode == MeasureSpec.EXACTLY ? sizeWidth : width + getLeft() + getPaddingRight()),
                (heightMode == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()));

    }

    //总的view
    private List<List<View>> mViews = new ArrayList<>();
    //添加每一行的view
    private List<View> mLinesView = new ArrayList<>();


    private List<Integer> mLineWidth = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mViews.clear();
        mLinesView.clear();
        mLineWidth.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            System.out.println("-------------" + i);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            if (lineWidth + childWidth > width - getPaddingLeft() - getPaddingRight()) {
                mViews.add(mLinesView);
                mLineHeight.add(lineHeight);
                mLineWidth.add(lineWidth);

                lineWidth = 0;
                mLinesView = new ArrayList<>();
            }

            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
            mLinesView.add(child);
        }


        mViews.add(mLinesView);
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);

        int currentHeight = getPaddingTop();


        for (int i = 0; i < mViews.size(); i++) {
            System.out.println("-----------" + i);
            List<View> views = mViews.get(i);
            int maxHeight = mLineHeight.get(i);
            int left = getPaddingLeft();

            for (int i1 = 0; i1 < views.size(); i1++) {
                System.out.println("" + i + "--" + i1);
                /*
                 * get(i1) 被写成了get(i)
                 */
                View child = views.get(i1);
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + layoutParams.leftMargin;
                int tc = currentHeight + layoutParams.topMargin;

                //放置的范围不包括margin
                int rc = lc + getMeasuredWidth();
                int bc = currentHeight + getMeasuredHeight();

                /*
                    调用的是child.layout不是layout
                 */
                child.layout(lc, tc, rc, bc);


                /*
                * 这里面出现过一次错误，因为child.getMeasuredWidth()被写成了getMeasuredWidth()
                * */
                left += layoutParams.leftMargin + layoutParams.rightMargin + child.getMeasuredWidth();
            }
            currentHeight += maxHeight;
        }

    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


}
