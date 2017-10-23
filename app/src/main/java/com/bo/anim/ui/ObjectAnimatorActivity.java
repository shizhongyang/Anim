package com.bo.anim.ui;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bo.anim.R;
import com.bo.anim.custom.MyPointView1;
import com.bo.anim.custom.MyTextView;
import com.bo.anim.util.CommUtil;

public class ObjectAnimatorActivity extends AppCompatActivity {

    private MyPointView1 mPointView;
    private TextView tv;
    private MyTextView tv2;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        mPointView = (MyPointView1)findViewById(R.id.pointview);
        tv = (TextView) findViewById(R.id.tv1);
        tv2 = (MyTextView) findViewById(R.id.tv2);
       // String extraShareElementInfo = MainActivity.EXTRA_SHARE_ELEMENT_INFO;
        Activity activity = MainActivity.activity;
        CommUtil.getInstance(this);
        img = (ImageView) findViewById(R.id.img);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPointViewAnimation();
            }

        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPropertyAnim();
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPropertyAnimOfObject();
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPropertyAnimOfKeyframe();
            }
        });
    }


/*
* public static ObjectAnimator ofObject(Object target, String propertyName,TypeEvaluator evaluator, Object... values)  */

    private void doPropertyAnimOfObject() {

        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText",new CharEvaluator(),new Character('A'),new Character('Z'));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tv2, charHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void doPointViewAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 300, 100);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 由于ValueAnimator和ObjectAnimator都具有ofPropertyValuesHolder（）函数，使用方法也差不多，
     * 相比而言，ValueAnimator的使用机会不多，
     * 这里我们就只讲ObjectAnimator中ofPropertyValuesHolder（）的用法。
     *
     *  public static ObjectAnimator ofPropertyValuesHolder(Object target,PropertyValuesHolder... values)
     *
     *public static PropertyValuesHolder ofFloat(String propertyName, float... values)
     public static PropertyValuesHolder ofInt(String propertyName, int... values)
     public static PropertyValuesHolder ofObject(String propertyName, TypeEvaluator evaluator,Object... values)
     public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
     *
     */
    private void doPropertyAnim() {
        PropertyValuesHolder rotationHolder = PropertyValuesHolder
                .ofFloat("Rotation",60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor",
                0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tv,rotationHolder,colorHolder);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

    }

    /*
    *public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
    *propertyName：动画所要操作的属性名
      values：Keyframe的列表，PropertyValuesHolder会根据每个Keyframe的设定，定时将指定的值输出给动画。
    *
    *
    * */

    private void doPropertyAnimOfKeyframe() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img,frameHolder);
        animator.setDuration(1000);
        animator.start();

    }

    /**
     *
     * ofFloat

    public static Keyframe ofFloat(float fraction)
    public static Keyframe ofFloat(float fraction, float value)

     * ofInt
    public static Keyframe ofInt(float fraction)
    public static Keyframe ofInt(float fraction, int value)

    */
    /**
     * 下面这个函数与上面的对应
     *
     *
     * 设置fraction参数，即Keyframe所对应的进度
     */
    //  public void setFraction(float fraction)
    /**
     * 设置当前Keyframe所对应的值
     */
    // public void setValue(Object value)
    /**
     * 设置Keyframe动作期间所对应的插值器
     */
    //  public void setInterpolator(TimeInterpolator interpolator)

    /*
    * public static Keyframe ofObject(float fraction)
        public static Keyframe ofObject(float fraction, Object value)
    * */

        /*
        * Keyframe frame0 = Keyframe.ofObject(0f, new Character('A'));
        Keyframe frame1 = Keyframe.ofObject(0.1f, new Character('L'));
        Keyframe frame2 = Keyframe.ofObject(1,new Character('Z'));

        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("CharText",frame0,frame1,frame2);
        frameHolder.setEvaluator(new CharEvaluator());
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv,frameHolder);
        animator.setDuration(3000);
        animator.start();
        * */




    public class CharEvaluator implements TypeEvaluator<Character> {
        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt  = (int)startValue;
            int endInt = (int)endValue;
            int curInt = (int)(startInt + fraction *(endInt - startInt));
            char result = (char)curInt;
            return result;
        }
    }

}
