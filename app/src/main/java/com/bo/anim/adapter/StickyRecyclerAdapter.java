package com.bo.anim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bo.anim.entity.TestBean;

import java.util.List;

public class StickyRecyclerAdapter extends RecyclerView.Adapter<StickyRecyclerAdapter.MyViewHolder> {

	private List<TestBean> list;
	private OnItemClickListener mOnItemClickListener;

	public StickyRecyclerAdapter(List<TestBean> list) {
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
		//������
		holder.tv.setText(list.get(position).getName());
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
		MyViewHolder holder = new MyViewHolder(View.inflate(viewGroup.getContext(),
				android.R.layout.simple_list_item_1, null));
		return holder;
	}
	
	public void addData(int position){
		TestBean testBean = new TestBean("ac","123");
		testBean.setName("ad");
		list.add(position,testBean);
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
