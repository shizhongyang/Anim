package com.bo.anim.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bo.anim.R;

public class AnimatorSetActivity extends AppCompatActivity {

    private TextView mTv1,mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_set);


        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doPlaySequentiallyAnimator();
            }


        });
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doPlayTogether();
            }


        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doPlaySequentiallyAnimator1();
            }


        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doPlayBuilder();
            }


        });
    }

    /**
     * //和前面动画一起执行
     public Builder with(Animator anim)
     //执行前面的动画后才执行该动画
     public Builder before(Animator anim)
     //执行先执行这个动画再执行前面动画
     public Builder after(Animator anim)
     //延迟n毫秒之后执行动画
     public Builder after(long delay)
     */
    private void doPlayBuilder() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = animatorSet.play(tv1BgAnimator);
        builder.with(tv1TranslateY);
        animatorSet.start();


        /*
        * ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv1TranslateY).with(tv2TranslateY).after(tv1BgAnimator);
        animatorSet.setDuration(2000);
        animatorSet.start();*/

    }

    private void doPlayTogether() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);

        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 400, 0);
        tv1TranslateY.setStartDelay(2000);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, 400, 0);
        tv2TranslateY.setStartDelay(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * public void playSequentially(Animator... items);
     public void playSequentially(List<Animator> items);

     所以这就是playSequentially的效果，即逐个播放动画，一个动画结束后，播放下一个动画
     */

    private void doPlaySequentiallyAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 300, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
       // animatorSet.playSequentially(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.playTogether(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private void doPlaySequentiallyAnimator1() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mTv1, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        tv1BgAnimator.setStartDelay(2000);

        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mTv1, "translationY", 0, 300, 0);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mTv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(tv1BgAnimator,tv1TranslateY,tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
/*
    public static interface AnimatorListener {
        *//**
         * 当AnimatorSet开始时调用
         *//*
        void onAnimationStart(Animator animation);

        *//**
         * 当AnimatorSet结束时调用
         *//*
        void onAnimationEnd(Animator animation);

        *//**
         * 当AnimatorSet被取消时调用
         *//*
        void onAnimationCancel(Animator animation);

        *//**
         * 当AnimatorSet重复时调用，由于AnimatorSet没有设置repeat的函数，所以这个方法永远不会被调用
         *//*
        void onAnimationRepeat(Animator animation);
    }*/
}
