package com.bo.anim.md.recycler.itemtouch;


import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.bo.anim.R;

/**
 * Created by TT on 2017-11-01.
 */

public class MyItemTouchHelperCallBack extends Callback {


    private ItemTouchMoveListener moveListener;

    public MyItemTouchHelperCallBack(ItemTouchMoveListener moveListener) {
        this.moveListener = moveListener;
    }



    //Callback 回调监听时候先调用。用来判断当前是什么操作，比如
    //判断方向（意思就是我要监听哪个方向的拖动）
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //方向：up,down,left,right

        //ItemTouchHelper.UP;   //1  0x0001
        //ItemTouchHelper.DOWN; //2  0x0010
        //ItemTouchHelper.LEFT;
        //ItemTouchHelper.RIGHT;
        //flags 进行&运算  0&0 -> 0  0&1 -> 0  1&1 -> 1
        //if(0x0011 & 0x0001) &运算 得到0x0001  就是向上
        //if(0x0011 & 0x0010) 得到0x0010  就是向下

        //0x0011 //这样就是返回上下

        //我要监听的拖拽方向是哪个方向。
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        //我要监听的swipe的侧滑方向是哪个方向
        int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;;
        int flags = makeMovementFlags(dragFlags,swipeFlags);
        return flags;
    }


    //是否允许长按拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //当移动的时候回调的方法--拖拽
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() !=target.getItemViewType()){
            return false;
        }
        //在拖拽的过程中不断的调用adapter.notifyItemMoved(from,to);
        boolean res = moveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return res;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        moveListener.onItemRemove(viewHolder.getAdapterPosition());
    }


    @Override
    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        //判断选中状态 IDLE 是闲置的状态
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        // 恢复
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        /*viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);//1~0
        viewHolder.itemView.setScaleY(1);//1~0*/
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            ViewHolder viewHolder, float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {

        float alpha = 1-Math.abs(dX)/viewHolder.itemView.getWidth();
        //dX:水平方向移动的增量（负：往左；正：往右）范围：0~View.getWidth  0~1
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            //透明度动画

            viewHolder.itemView.setAlpha(alpha);//1~0
            viewHolder.itemView.setScaleX(alpha);//1~0
            viewHolder.itemView.setScaleY(alpha);//1~0
        }

        if (alpha == 0){
            viewHolder.itemView.setAlpha(1);//1~0
            viewHolder.itemView.setScaleX(1);//1~0
            viewHolder.itemView.setScaleY(1);//1~0
        }

        //判断达到一定的位置就不滑动了。
        viewHolder.itemView.setTranslationX(-0.5f*viewHolder.itemView.getWidth());


        //这个方法会自动处理setTranslationX 可以注释掉是什么效果
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
