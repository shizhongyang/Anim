package com.bo.anim.ui.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bo.anim.R;

public class FirstCustomViewGroupActivity extends AppCompatActivity {
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout
            .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private RelativeLayout first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_custom_view_group);
        first = (RelativeLayout) findViewById(R.id.first);
        ImageView item1 = new ImageView(this);
        item1.setImageResource(R.drawable.leak_canary_icon);//设置图片
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);//与父容器的上侧对齐
        lp.leftMargin=30;
        lp.topMargin=30;
        //item1.setId(1);//设置这个View 的id
        item1.setLayoutParams(lp);//设置布局参数
        first.addView(item1);//RelativeLayout添加子View

    }
}
