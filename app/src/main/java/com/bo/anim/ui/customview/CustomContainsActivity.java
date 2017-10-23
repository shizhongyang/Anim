package com.bo.anim.ui.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bo.anim.R;

public class CustomContainsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_contains);
    }


    public void click(View view){
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn1:
                intent.setClass(this,CustomViewActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this,CustomReaBookActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this,CustomDoughnutProgressActivity.class);
                break;
            case R.id.btn4:
                intent.setClass(this,CustomArcActivity.class);
                break;
            case R.id.btn5:
                intent.setClass(this,CustomCameraAndMatrixActivity.class);
                break;
        }
        startActivity(intent);
    }
}
