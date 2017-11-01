package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.bo.anim.R;
import com.bo.anim.adapter.MyRecyclerAdapter;
import com.bo.anim.md.recycler.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAddHeaderActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_add_header);
        initData();
        initView();


    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(list);
        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(adapter);

        TextView headerView = new TextView(this);
        //		TextView tv = headerView.findViewById(id);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 20);
        headerView.setLayoutParams(params);
        headerView.setText("我是HeaderView");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        wrapper.addHeaderView(headerView);
        wrapper.addHeaderView(t2);


        TextView footerView = new TextView(this);
        params = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
        footerView.setLayoutParams(params);
        footerView.setText("我是FooterView");
        wrapper.addFootView(footerView);

        /*mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayout.VERTICAL,false));//默认垂直*/
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mRecyclerView.setAdapter(wrapper);

    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            list.add("item" + i);
        }
    }
}
