package com.bo.anim.ui.customview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.base.BaseRecyclerAdapter;
import com.bo.anim.base.BaseRecyclerHolder;
import com.bo.anim.custom.zhihu.AdImageView;

import java.util.ArrayList;
import java.util.List;

public class ZhiHuActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_hu);
        initDataAndView();
    }

    private void initDataAndView() {


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<String> mockDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mockDatas.add(i + "");
        }
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ItemAdapter(getApplicationContext(),mockDatas));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = (AdImageView) view.findViewById(R.id.id_iv_ad);
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        adImageView.setDx(mLinearLayoutManager.getHeight() - view.getTop());
                    }
                }
            }
        });
    }


    public class ItemAdapter extends BaseRecyclerAdapter<String> {

        public ItemAdapter(Context context, List<String> datas) {
            super(context, R.layout.zhi_hu_item, datas);
        }

        @Override
        public void convert(BaseRecyclerHolder holder, String item, int position) {
            if (position > 0 && position % 7 == 0) {
                holder.setVisible(R.id.id_tv_title, false);
                holder.setVisible(R.id.id_tv_desc, false);
                holder.setVisible(R.id.id_iv_ad, true);
            } else {
                holder.setVisible(R.id.id_tv_title, true);
                holder.setVisible(R.id.id_tv_desc, true);
                holder.setVisible(R.id.id_iv_ad, false);
            }
        }
    }
}
