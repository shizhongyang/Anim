package com.bo.anim.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bo.anim.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

	private List<String> list;
	private OnItemClickListener mOnItemClickListener;

	public MyRecyclerAdapter(List<String> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder{
		TextView tv;

		public MyViewHolder(View view) {
			super(view);
			tv = (TextView)view.findViewById(android.R.id.text1);
			
		}
		
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		TextView tv = holder.tv;
		tv.setText(list.get(position));
		tv.setBackgroundColor(R.color.colorPrimary);

		//tv.setOnClickListener();
		if(mOnItemClickListener!=null){
			holder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(v, position);
				}
			});
		}
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// ViewHolder
		return new
				MyViewHolder(LayoutInflater.from(viewGroup.getContext())
				.inflate(android.R.layout.simple_list_item_1, viewGroup,false));
		//new MyViewHolder(View.inflate(viewGroup.getContext(),android.R.layout.simple_list_item_1, null));
		//这样得到的布局不会铺满屏幕
		/*
		*     if (root != null) {
                        if (DEBUG) {
                            System.out.println("Creating params from root: " +
                                    root);
                        }
                        // Create layout params that match root, if supplied
                        params = root.generateLayoutParams(attrs);
                        if (!attachToRoot) {
                            // Set the layout params for temp if we are not
                            // attaching. (If we are, we use addView, below)
                            temp.setLayoutParams(params);
                        }
                    }
                    因为无法得到父布局的LayoutParams,就没有办法给目标布局设置LayoutParams
		* */


	}
	
	public void addData(int position){
		list.add(position,"additem"+position);
		//
		//notifyDataSetChanged();
		notifyItemInserted(position);
	}
	public void removeData(int position){
		list.remove(position);
		notifyItemRemoved(position);
	}
	
	public interface OnItemClickListener{
		void onItemClick(View view, int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener = listener;
	}
}
