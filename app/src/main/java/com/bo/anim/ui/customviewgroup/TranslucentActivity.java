package com.bo.anim.ui.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bo.anim.R;
import com.bo.anim.customviewgroup.scroll.MyScrollView;
import com.bo.anim.customviewgroup.scroll.TranslucentListener;


public class TranslucentActivity extends AppCompatActivity implements TranslucentListener {

    private Toolbar toolbar;
    private MyScrollView scv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scv = (MyScrollView) findViewById(R.id.scrollView);
        scv.setTranslucentListener(this);

    }



    @Override
    public void onTranlucent(float alpha) {
        toolbar.setAlpha(alpha);
    }
}