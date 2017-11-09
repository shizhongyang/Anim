package com.bo.anim.ui.customviewgroup;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bo.anim.R;
import com.bo.anim.customviewgroup.listview.MyListView;
import com.bo.anim.customviewgroup.listview.onRefreshListener;

import java.util.ArrayList;

public class MyListViewActivity extends AppCompatActivity {
    private MyListView listViewNoText;
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
        listViewNoText.setRefreshListener(new onRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("---开始刷新");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("---刷新结束");
                        listViewNoText.onFinish();
                    }
                },3000);

            }
        });

        listViewNoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"jjj",Toast.LENGTH_SHORT)
                        .show();

            }
        });
        listViewNoText.requestLayout();
    }

}
