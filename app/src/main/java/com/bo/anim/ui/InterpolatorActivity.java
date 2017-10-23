package com.bo.anim.ui;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.bo.anim.R;

public class InterpolatorActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        tv = (TextView) findViewById(R.id.tv1);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanim(new BounceInterpolator());
            }

        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanim(new LinearInterpolator());
            }

        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanim(new MyInterploator());
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanimTypeEvaluator(new MyEvaluator());
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanimTypeEvaluator(new ReverseEvaluator());
            }
        });

        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanimArgbEvaluator();
            }
        });
    }

    private void doanim(Interpolator interpolator) {
        ValueAnimator animator = ValueAnimator.ofInt(0,600);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                tv.layout(tv.getLeft(),curValue,tv.getRight(),curValue+tv.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setInterpolator(interpolator);
        //animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void doanimTypeEvaluator(TypeEvaluator evaluator) {
        ValueAnimator animator = ValueAnimator.ofInt(0,600);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                tv.layout(tv.getLeft(),curValue,tv.getRight(),curValue+tv.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setEvaluator(evaluator);
        animator.start();
    }

    private void doanimArgbEvaluator () {
        ValueAnimator animator = ValueAnimator.ofInt(0xffffff00,0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                tv.setBackgroundColor(curValue);

            }
        });

        animator.start();
    }

    public class MyInterploator implements Interpolator{
        @Override
        public float getInterpolation(float input) {
            return 1-input;
        }
    }


    //在原本位置上面加上200
    public class MyEvaluator implements TypeEvaluator<Integer>{
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int startInt = startValue;
            return (int)(200+startInt + fraction * (endValue - startInt));
        }
    }

    public class ReverseEvaluator implements TypeEvaluator<Integer> {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int startInt = startValue;
            return (int) (endValue - fraction * (endValue - startInt));
        }
    }
}
