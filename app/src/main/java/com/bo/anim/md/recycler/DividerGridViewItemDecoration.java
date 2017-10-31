package com.bo.anim.md.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class DividerGridViewItemDecoration extends ItemDecoration {
	
	private Drawable mDivider;
	private int[] attrs= new int[]{
			android.R.attr.listDivider
	};
	
	public DividerGridViewItemDecoration(Context context) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs);
		mDivider = typedArray.getDrawable(0);
		typedArray.recycle();
	}
	
	@Override
	public void onDraw(Canvas c, RecyclerView parent, State state) {
		drawVertical(c,parent);
		drawHorizontal(c,parent);
	}
	
	private void drawHorizontal(Canvas c, RecyclerView parent) {
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			LayoutParams params = (LayoutParams) child.getLayoutParams();
			int left = child.getLeft() - params.leftMargin;
			int right = child.getRight()+ params.rightMargin;
			int top = child.getBottom() + params.bottomMargin;
			int bottom = top + mDivider.getIntrinsicHeight();
			
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
		
		
	}

	private void drawVertical(Canvas c, RecyclerView parent) {
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			LayoutParams params = (LayoutParams) child.getLayoutParams();
			int left = child.getRight() + params.rightMargin;
			int right = left + mDivider.getIntrinsicWidth();
			int top = child.getTop() - params.topMargin;
			int bottom = child.getBottom() + params.bottomMargin;
			
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
		
	}

	@Override
	@Deprecated
	public void getItemOffsets(Rect outRect, int itemPosition,
			RecyclerView parent) {
		int right = mDivider.getIntrinsicWidth();
		int bottom = mDivider.getIntrinsicHeight();
		if(isLastColum(itemPosition,parent)){//�Ƿ������һ��
//			outRect.set(0, 0, 0, bottom);
			right = 0;
		}
		if(isLastRow(itemPosition,parent)){//�����һ��
//			outRect.set(0, 0, right, 0);
			bottom = 0;
		}
		outRect.set(0, 0, right, bottom);
		
	}

	/**
	 * @param itemPosition
	 * @param parent
	 * @return
	 */
	private boolean isLastRow(int itemPosition, RecyclerView parent) {
		int spanCount =  getSpanCount(parent);
		LayoutManager layoutManager = parent.getLayoutManager();
		if(layoutManager instanceof GridLayoutManager){
			int childCount = parent.getAdapter().getItemCount();
			int lastRowCount = childCount%spanCount;
			if(lastRowCount==0||lastRowCount<spanCount){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param itemPosition
	 * @param parent
	 * @return
	 */
	private boolean isLastColum(int itemPosition, RecyclerView parent) {
		LayoutManager layoutManager = parent.getLayoutManager();
		if(layoutManager instanceof GridLayoutManager){
			int spanCount = getSpanCount(parent);
			if((itemPosition+1)%spanCount==0){
				return true;
			}
		}
		return false;
	}
	
	private int getSpanCount(RecyclerView parent){
		LayoutManager layoutManager = parent.getLayoutManager();
		if(layoutManager instanceof GridLayoutManager){
			GridLayoutManager lm = (GridLayoutManager)layoutManager;
			int spanCount = lm.getSpanCount();
			return spanCount;
		}
		return 0;
	}

}
