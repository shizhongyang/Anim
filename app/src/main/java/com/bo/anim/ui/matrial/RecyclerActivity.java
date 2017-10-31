package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.bo.anim.R;
import com.bo.anim.adapter.MyRecyclerAdapter;
import com.bo.anim.md.recycler.DividerItemDecorationTest;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView recylerview;
    private ArrayList<String> list;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            list.add("item" + i);
        }
        recylerview = (RecyclerView) findViewById(R.id.recycler);
        adapter = new MyRecyclerAdapter(list);

        recylerview.setLayoutManager(new LinearLayoutManager(this,
                LinearLayout.VERTICAL,false));//默认垂直
        //recylerview.setLayoutManager(new GridLayoutManager(this,3));
        recylerview.setAdapter(adapter);
        //recylerview.addItemDecoration(new DividerItemDecoration1(this, LinearLayout.VERTICAL));

        //添加标签
        //recylerview.addItemDecoration(new LeftAndRightTagDecoration(this));
        //组合使用

        /*recylerview.addItemDecoration(new LeftAndRightTagDecoration(this));
        DividerItemDecoration decor = new DividerItemDecoration(this);
        decor.setOrientation(LinearLayout.HORIZONTAL);
        recylerview.addItemDecoration(decor);*/

        recylerview.addItemDecoration(new DividerItemDecorationTest(this,DividerItemDecoration.VERTICAL));

        recylerview.setItemAnimator(new DefaultItemAnimator());
    }
}
