package com.bo.anim.ui.customviewgroup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bo.anim.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);

        MyAdapter adapter = new MyAdapter(this, list);
        viewPager.setAdapter(adapter);

        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                48, getResources().getDisplayMetrics()));
        viewPager.setCurrentItem(1);
        viewPager.setPageTransformer(false,new ScaleTransformer(getApplicationContext()));
    }


    public class MyAdapter extends PagerAdapter {
        private List<Integer> list;
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context, List<Integer> list) {
            this.context = context;
            this.list = list;
            inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.activity_view_pager_item, container, false);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    public class ScaleTransformer implements ViewPager.PageTransformer {
        private Context context;
        private float elevation;

        public ScaleTransformer(Context context) {
            this.context = context;
            elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    20, context.getResources().getDisplayMetrics());
        }

        @Override
        public void transformPage(View page, float position) {

            if (0.0f <= position && position <= 1.0f) {
                ((CardView) page).setCardElevation((1 - position) * elevation);
                Log.i("-----", "大于0: "+position);
            } else if (-1.0f <= position && position < 0.0f) {
                ((CardView) page).setCardElevation((1 + position) * elevation);
                Log.i("-----", "小于0: "+position);
            }
        }
    }
}
