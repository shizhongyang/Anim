package com.bo.anim.ui.matrial;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bo.anim.R;

public class RevealEffectActivity extends AppCompatActivity {
    LinearLayout linearScale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);


        Button btnTestScale = (Button) findViewById(R.id.btnTestScale);
        linearScale = (LinearLayout) findViewById(R.id.linearScale);
        btnTestScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator revealAnimator = ObjectAnimator.ofFloat( //缩放X 轴的
                        linearScale, "scaleX", 0, 2);
                ObjectAnimator revealAnimator1 = ObjectAnimator.ofFloat(//缩放Y 轴的
                        linearScale, "scaleY", 0, 2);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(500);//设置播放时间
                set.setInterpolator(new LinearInterpolator());//设置播放模式，这里是平常模式
                set.playTogether(revealAnimator, revealAnimator1);//设置一起播放
                set.start();
            }
        });
    }
}
