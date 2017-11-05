package com.bo.anim.ui.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.bo.anim.R;
import com.bo.anim.customviewgroup.listview.MyListView;

import java.util.ArrayList;

public class MyListViewActivity extends AppCompatActivity {
    MyListView listViewNoText;
    private ArrayList<String> mdatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);

        init();
    }

    public void init(){
        listViewNoText = (MyListView) findViewById(R.id.listview);
        for (int i = 0; i < 20; i++) {
            mdatas.add("--" + i);
        }


        listViewNoText.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,mdatas));

        listViewNoText.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

}
