package com.bo.anim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bo.anim.R;
import com.bo.anim.ui.customview.CustomContainsActivity;
import com.bo.anim.ui.customviewgroup.FirstCustomViewGroupActivity;
import com.bo.anim.ui.event.EventActivity;
import com.bo.anim.ui.matrial.AppLayoutActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    public static final String TRANSITION_NAME_SHARE = "share";
    private ImageView img;
    public static final String EXTRA_SHARE_ELEMENT_INFO = "share_element_info";
    public static Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);

        tv = (TextView) findViewById(R.id.tv1);
      //  activity = this;

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InterpolatorActivity.class));
            }

        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OfObjectActivity.class));
            }

        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ObjectAnimatorActivity.class));
            }

        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AnimatorSetActivity.class));
            }

        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ImageCacheActivity.class));
            }

        });
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomContainsActivity.class));
            }

        });
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FirstCustomViewGroupActivity.class));
            }

        });

        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EventActivity.class));
            }

        });
        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AppLayoutActivity.class));
            }

        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareElementActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    img.setTransitionName(TRANSITION_NAME_SHARE);
                    ActivityOptionsCompat compat = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(MainActivity.this,
                                    img, TRANSITION_NAME_SHARE);
                    startActivity(intent, compat.toBundle());
                } else {
                /*    ShareElementInfo info = new ShareElementInfo();
                    info.convertOriginalInfo(imageView);
                    intent.putExtra(EXTRA_SHARE_ELEMENT_INFO, info);
                    startActivity(intent);
                    overridePendingTransition(0, 0);*/
                }
            }
        });
    }

    public static void save(final int a){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(a);
            }
        });
    }

}
