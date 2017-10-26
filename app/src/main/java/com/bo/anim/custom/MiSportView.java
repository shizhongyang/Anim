package com.bo.anim.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bo.anim.R;
import com.bo.anim.util.DensityUtils;

/**
 * Created by TT on 2017-10-24.
 */

public class MiSportView extends View {


    private static final float BIG_CIRCLE_SHAKE_RADIUS = 20;
    private static final float BIG_CIRCLE_SHAKE_OFFSET = 0.5f;
    private static final float BIG_CIRCLE_SIZE = 16;

    private static final float CIRCLE_BLUR_SIZE = 16;
    private Paint bigCirclePaint;
    /**
     * 光晕画笔
     **/
    private Paint blurPaint;


    private float degree = 0;

    private float circleX;
    private float circleY;

    public MiSportView(Context context) {
        this(context, null);
    }

    public MiSportView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiSportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        bigCirclePaint = new Paint();
        bigCirclePaint.setStrokeWidth(DensityUtils.dp2px(getContext(), BIG_CIRCLE_SIZE));
        bigCirclePaint.setStyle(Paint.Style.STROKE);
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setColor(Color.parseColor("#ffffffff"));

        blurPaint = new Paint(bigCirclePaint);
        blurSize = DensityUtils.dp2px(getContext(), CIRCLE_BLUR_SIZE);
        PathEffect pathEffect1 = new CornerPathEffect(DensityUtils.dp2px(getContext(),
                BIG_CIRCLE_SHAKE_RADIUS));
        PathEffect pathEffect2 = new DiscretePathEffect(DensityUtils.dp2px(getContext(),
                BIG_CIRCLE_SHAKE_RADIUS),
                DensityUtils.dp2px(getContext(), BIG_CIRCLE_SHAKE_OFFSET));
        PathEffect pathEffect = new ComposePathEffect(pathEffect1, pathEffect2);
        bigCirclePaint.setPathEffect(pathEffect);


        anim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
        circleX = width * 0.5f;
        circleY = height * 0.5f;

        float bigCircleRadius = width * 0.4f;
        Shader bigCircleLinearGradient = new LinearGradient(
                circleX - bigCircleRadius, circleY,
                circleX + bigCircleRadius, circleY,
                ContextCompat.getColor(getContext(), R.color.whiteTransparent),
                ContextCompat.getColor(getContext(), R.color.white),
                Shader.TileMode.CLAMP);
        bigCirclePaint.setShader(bigCircleLinearGradient);

        Shader blurLinearGradient = new LinearGradient(
                circleX, circleY,
                circleX + bigCircleRadius, circleY,
                ContextCompat.getColor(getContext(), R.color.transparent),
                ContextCompat.getColor(getContext(), R.color.white),
                Shader.TileMode.CLAMP);
        blurPaint.setShader(blurLinearGradient);

    }
    ValueAnimator animator;
    private void anim() {

        animator = ValueAnimator.ofFloat(0, 360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                System.out.println(degree);
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(12240);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private int circleColor = 0;
    private float blurSize = 0;
    /**
     * 圆环从透明到实体的显示进度 %
     **/
    private float circleAlphaProgress = 1;
    /**
     * 圆环光晕效果层数
     **/
    private static final int CIRCLE_BLUR_LAYER_AMOUNT = 4;

    private RectF blurOvalRectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(degree, circleX, circleY);
        float bigCircleRadius = getWidth() * 0.4f;
        canvas.drawCircle(circleX, circleY, bigCircleRadius, bigCirclePaint);

        // 光晕
        blurPaint.setAlpha((int) (Color.alpha(circleColor) * circleAlphaProgress));
        for (int i = 0; i < CIRCLE_BLUR_LAYER_AMOUNT; i++) {
            blurPaint.setAlpha(0xff * (CIRCLE_BLUR_LAYER_AMOUNT - i) / (CIRCLE_BLUR_LAYER_AMOUNT * 3));
            blurOvalRectF.set(circleX - bigCircleRadius, circleY - bigCircleRadius,
                    circleX + bigCircleRadius + i * blurSize / CIRCLE_BLUR_LAYER_AMOUNT, circleY + bigCircleRadius);
            canvas.drawOval(blurOvalRectF, blurPaint);
        }

        canvas.restore();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }
}
