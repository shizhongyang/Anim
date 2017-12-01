package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bo.anim.R;
import com.bo.anim.md.weibo.android.AppBarLayout;

public class AppBarLayoutActivity extends AppCompatActivity {

    private AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitle("这里是Title");
        // toolbar.setSubtitle("这里是子标题");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            }
        });

    }
}
