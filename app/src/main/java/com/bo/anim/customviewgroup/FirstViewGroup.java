package com.bo.anim.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bo.anim.R;

/**
 * Created by TT on 2017-10-19.
 */

public class FirstViewGroup extends RelativeLayout {



    public FirstViewGroup(Context context) {
        this(context,null);
    }

    public FirstViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FirstViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View ContentView = View.inflate(context, R.layout.text, this);

    }




 /*   @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();

    }*/




}
