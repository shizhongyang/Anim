package com.bo.anim.ui.matrial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bo.anim.R;
import com.bo.anim.md.recycler.HeaderAndFooterWrapperY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedRecyclerActivity extends AppCompatActivity {

    public static final String KEY_ICON = "icon";
    public static final String KEY_COLOR = "color";

    protected List<Map<String, Integer>> mSampleList;

    private RecyclerView mRecyclerView;
    private MyHeaderAndFooterWrapper myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_recycler);
        myAdapter = new MyHeaderAndFooterWrapper();
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);

        Map<String, Integer> map;
        mSampleList = new ArrayList<>();

        int[] icons = {
                R.mipmap.icon_1,
                R.mipmap.icon_2,
                R.mipmap.icon_3};

        int[] colors = {
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna};

        for (int i = 0; i < 5; i++) {
            map = new HashMap<>();
            map.put(KEY_ICON, icons[i%3]);
            map.put(KEY_COLOR, colors[i%3]);
            mSampleList.add(map);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(myAdapter);
    }

    class MyHeaderAndFooterWrapper extends HeaderAndFooterWrapperY {

        @Override
        public MyViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
            return MyViewHolder.createViewHolder(parent.getContext(),view);
        }

        @Override
        public MyViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
            TextView  view = new TextView(parent.getContext());
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
            return MyViewHolder.createViewHolder(parent.getContext(),view);
        }

        @Override
        public RecyclerView.ViewHolder onCreateGeneralViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder1(LayoutInflater.from(
                    AdvancedRecyclerActivity.this).inflate(R.layout.list_item, parent,
                    false));
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText("这是一个Header");
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText("这是一个Footer");
        }

        @Override
        public void onBindGeneralViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder1)holder).imageViewIcon.setImageResource(mSampleList.get(position % 3).get(KEY_ICON));
            ((MyViewHolder1)holder).itemView.setBackgroundResource(mSampleList.get(position % 3).get(KEY_COLOR));
        }
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        ImageView imageViewIcon;

        MyViewHolder1(View view)
        {
            super(view);
            imageViewIcon = (ImageView) view.findViewById(R.id.image_view_icon);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(View itemView) {
            super(itemView);
        }

        static MyViewHolder createViewHolder(Context c, View v){
            return new MyViewHolder(v);
        }
    }
}

