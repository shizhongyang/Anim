package com.bo.anim.ui.customviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.ui.customview.MiClockActivity;

public class ViewGroupContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_container);
    }

    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn1:
                intent.setClass(this, FirstCustomViewGroupActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this, FlowLayoutActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this, ParallelActivity.class);
                break;
            case R.id.btn4:
                intent.setClass(this, DiscrollActivity.class);
                break;
            case R.id.btn5:
                intent.setClass(this, MyListViewActivity.class);
                break;
            case R.id.btn6:
                intent.setClass(this, MiClockActivity.class);
                break;
            case R.id.btn7:
                intent.setClass(this, MyListViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
