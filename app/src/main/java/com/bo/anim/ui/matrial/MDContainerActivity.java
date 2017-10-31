package com.bo.anim.ui.matrial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bo.anim.R;

public class MDContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdcontainer);
    }


    public void click(View view){
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn1:
                intent.setClass(this,AppLayoutActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this,RecyclerActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this,StickyRecyclerActivity.class);
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
        }
        startActivity(intent);
    }
}
