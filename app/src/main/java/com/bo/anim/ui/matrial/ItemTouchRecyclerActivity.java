package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bo.anim.R;
import com.bo.anim.md.recycler.itemtouch.DataUtils;
import com.bo.anim.md.recycler.itemtouch.MyItemTouchHelperCallBack;
import com.bo.anim.md.recycler.itemtouch.QQAdapter;
import com.bo.anim.md.recycler.itemtouch.QQMessage;
import com.bo.anim.md.recycler.itemtouch.StartDragListener;

import java.util.List;

public class ItemTouchRecyclerActivity extends AppCompatActivity implements StartDragListener {
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_recycler);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<QQMessage> list = DataUtils.init();
        QQAdapter adapter = new QQAdapter(list,this);
        recyclerView.setAdapter(adapter);
        //条目触摸帮助类
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallBack(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder holder) {
        itemTouchHelper.startDrag(holder);
    }
}
