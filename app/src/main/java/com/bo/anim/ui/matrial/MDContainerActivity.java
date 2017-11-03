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


    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn1:
                intent.setClass(this, AppBarLayoutActivity.class);
                break;
            case R.id.btn2:
                intent.setClass(this, RecyclerActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(this, StickyRecyclerActivity.class);
                break;
            case R.id.btn4:
                intent.setClass(this, RecyclerAddHeaderActivity.class);
                break;
            case R.id.btn5:
                intent.setClass(this, AdvancedRecyclerActivity.class);
                break;
            case R.id.btn6:
                intent.setClass(this, ItemTouchRecyclerActivity.class);
                break;
            case R.id.btn7:
                intent.setClass(this, AppBarLayout_CollapsingActivity.class);
                break;
            case R.id.btn8:
                intent.setClass(this, AppBarLayout_ViewpagerActivity.class);
                break;
            case R.id.btn9:
                intent.setClass(this, RevealEffectActivity.class);
                break;
        }
        startActivity(intent);
    }
}
