package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bo.anim.R;
import com.bo.anim.adapter.StickyRecyclerAdapter;
import com.bo.anim.entity.TestBean;
import com.bo.anim.md.recycler.SectionDecoration;

import java.util.ArrayList;
import java.util.List;

public class StickyRecyclerActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<TestBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recycler);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        StickyRecyclerAdapter adapter = new StickyRecyclerAdapter(mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(mDatas.get(position).getName().charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return mDatas.get(position).getName().substring(0, 1).toUpperCase();
            }
        }));
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        TestBean bean = new TestBean("aa","123");
        TestBean bean1 = new TestBean("ab","123");
        TestBean bean2 = new TestBean("ac","123");
        TestBean bean3 = new TestBean("ad","123");
        TestBean bean4 = new TestBean("ba","123");
        TestBean bean5 = new TestBean("bc","123");
        TestBean bean6 = new TestBean("ba","123");
        TestBean bean7 = new TestBean("ca","123");
        TestBean bean8 = new TestBean("ca","123");
        TestBean bean9 = new TestBean("cd","123");
        TestBean bean10 = new TestBean("da","123");
        TestBean bean11 = new TestBean("da","123");
        TestBean bean12 = new TestBean("da","123");
        TestBean bean13 = new TestBean("ea","123");
        TestBean bean14 = new TestBean("ea","123");
        TestBean bean15 = new TestBean("ea","123");
        TestBean bean16 = new TestBean("fa","123");
        TestBean bean17 = new TestBean("fa","123");
        TestBean bean18 = new TestBean("aa","123");
        mDatas.add(bean);
        mDatas.add(bean1);
        mDatas.add(bean2);
        mDatas.add(bean3);
        mDatas.add(bean4);
        mDatas.add(bean5);
        mDatas.add(bean6);
        mDatas.add(bean7);
        mDatas.add(bean8);
        mDatas.add(bean9);
        mDatas.add(bean10);
        mDatas.add(bean11);
        mDatas.add(bean12);
        mDatas.add(bean13);
        mDatas.add(bean14);
        mDatas.add(bean15);
        mDatas.add(bean16);
        mDatas.add(bean17);
        mDatas.add(bean18);

    }
}
