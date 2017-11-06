package com.bo.anim.customviewgroup.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by TT on 2017-11-06.
 */

public class ParallaxLayoutInflater extends LayoutInflater {


    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);

       // setFactory(new );
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this,newContext);
    }


    //自定义工厂类，视图创建工厂类
    class PatallaxFactory implements Factory{

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }
}
