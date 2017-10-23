package com.bo.anim.custom.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TT on 2017-10-20.
 */

public class MultiFingerTestView extends View {

    String TAG = "shi";


    public MultiFingerTestView(Context context) {
        super(context);
    }

    public MultiFingerTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiFingerTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /*
    *   从 0 开始，自动增长。
        如果之前落下的手指抬起，后面手指的 Index 会随之减小。
        Index 变化趋向于第一次落下的数值(落下手指时，前面有空缺会优先填补空缺)。
        对 move 事件无效。

        --------------------------------
        | ACTION_DOWN 监听第一个手指按下 |
        | ACTION_UP   最后一个手指抬起   |
        --------------------------------

        event.getActionIndex() 对 move 事件无效。
    * */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index = event.getActionIndex();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"第一个手指按下");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"最后一个手指抬起");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG,"第"+(index+1)+"个手指按下");
                break;
            case MotionEvent.ACTION_POINTER_UP
                    :
                Log.i(TAG,"第"+(index+1)+"个手指抬起");
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    Log.i("TAG", "pointerIndex="+i+", pointerId="+event.getPointerId(i));
                    // TODO
                }
                break;
        }
        return super.onTouchEvent(event);
    }







}
