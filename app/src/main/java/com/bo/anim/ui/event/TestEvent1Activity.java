package com.bo.anim.ui.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bo.anim.R;
import com.bo.anim.custom.event.MyButton;

public class TestEvent1Activity extends Activity {

    private MyButton btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event1);

        btn1 = (MyButton) findViewById(R.id.btn1);

    /*    btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("----+onTouch被调用");
                //return true; 返回true将会拦截 onTouchEvent  相当于事件被消费
                return true;
            }
        });*/


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("----+OnClickListener被调用");
            }
        });

     /*   btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });*/
    }
}
