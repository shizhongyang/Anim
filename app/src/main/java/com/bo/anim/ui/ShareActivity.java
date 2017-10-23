package com.bo.anim.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bo.anim.R;

public class ShareActivity extends AppCompatActivity {

    private final long ANIMATOR_DURATION = 1000;

    private ImageView mImageView;
  //  private FKJShareElement mShareElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
    }




}
