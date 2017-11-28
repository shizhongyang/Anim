package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bo.anim.R;
import com.bo.anim.adapter.MyRecyclerAdapter;
import com.bo.anim.md.recycler.DividerItemDecorationTest;
import com.bo.anim.md.recycler.scroll.FABAction;
import com.bo.anim.md.recycler.scroll.FABScrollListener;

import java.util.ArrayList;

public class RecyclerAndFABBehaviorActivity extends AppCompatActivity implements FABAction{
    private RecyclerView recylerview;
    private ArrayList<String> list;
    private MyRecyclerAdapter adapter;
    private ImageButton ib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            list.add("item" + i);
        }
        recylerview = (RecyclerView) findViewById(R.id.recycler);
        ib = (ImageButton) findViewById(R.id.ib);
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
        recylerview.addOnScrollListener(new FABScrollListener(this));
    }

    public void show(View view){
        //view 是锚点 向上找父容器CoordinateLayout 根布局不是CoordinateLayout FAB则会被Snackbar遮住
        Snackbar.make(view,"Hello",Snackbar.LENGTH_SHORT).setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"adg",Toast.LENGTH_SHORT).show();
            }
        }).show();

    }

    @Override
    public void onShow() {
        ib.animate()
                .translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300);
    }

    @Override
    public void onHide() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)ib.getLayoutParams();
        ib.animate()
                .translationY(ib.getHeight()+layoutParams.bottomMargin)
                .setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300);
    }
}
