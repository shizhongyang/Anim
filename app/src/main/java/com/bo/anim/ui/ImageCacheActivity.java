package com.bo.anim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bo.anim.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageCacheActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cache);
        String url = "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg";
        img = (ImageView) findViewById(R.id.img);
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
       // Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(mIv);
    }
}
