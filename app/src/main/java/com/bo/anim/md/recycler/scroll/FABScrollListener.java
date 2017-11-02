package com.bo.anim.md.recycler.scroll;

import android.support.v7.widget.RecyclerView;

/**
 * Created by TT on 2017-11-02.
 */

public class FABScrollListener extends RecyclerView.OnScrollListener {

    private static final int FLAG = 20;

    private FABAction action;

    private int distance = 0;

    private boolean visible = true;

    public FABScrollListener(FABAction action) {
        this.action = action;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (distance > FLAG && visible) {
            visible = false;
            action.onHide();
            distance = 0;
            //这里应该是小于-FlAG 因为如果直接小于FLAG 当distance大于FLAG时候 就会立马进入下一个判断，导致无法出现效果
        } else if (distance < -FLAG && !visible) {
            visible = true;
            action.onShow();
            distance = 0;
        }

        if ((visible && dy > 0) || (!visible && dy < 0))
            distance += dy;


        System.out.println("-------------dy="+dy+"distance="+distance+"visible="+visible);
    }
}
