package com.bo.anim.ui.matrial;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bo.anim.R;
import com.bo.anim.base.BaseFragmentAdapter;
import com.bo.anim.md.weibo.HeaderPageBehavior;
import com.bo.anim.md.weibo.OnPagerStateListener;
import com.bo.anim.ui.frgment.BlankFragment;

import java.util.ArrayList;
import java.util.List;

public class WeiboFindPageActivity extends AppCompatActivity implements OnPagerStateListener {

    ViewPager mViewPager;
    List<Fragment> mFragments;


    String[] mTitles = new String[]{
            "主页", "微博", "相册"
    };
    private View mHeaderView;
    private HeaderPageBehavior mHeaderPagerBehavior;
    private View mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_find_page);

        initView();
        initData();
    }

    private void initData() {
        setupViewPager();
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                mHeaderView.getLayoutParams();
        mHeaderPagerBehavior = (HeaderPageBehavior) layoutParams.getBehavior();
        mHeaderPagerBehavior.setmPagerStateListener(this);

    }
    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            BlankFragment listFragment = BlankFragment.newInstance(mTitles[i]);
            mFragments.add(listFragment);
        }
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);

        viewPager.setAdapter(adapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mHeaderView = findViewById(R.id.id_weibo_header);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBack();
            }
        });
        mIvBack.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }

    private void handleBack() {
        if(mHeaderPagerBehavior.isClosed()){
            System.out.println("--------");
            mHeaderPagerBehavior.openPager();
            return;
        }
        finish();
    }

    @Override
    public void onPagerClosed() {
        Toast.makeText(this, "关闭了", Toast.LENGTH_SHORT).show();
        mIvBack.setVisibility(View.VISIBLE);
        for(Fragment fragment:mFragments){
            BlankFragment listFragment= (BlankFragment) fragment;
            listFragment.tooglePager(false);
        }
    }

    @Override
    public void onPagerOpened() {
        Toast.makeText(this, "打开了", Toast.LENGTH_SHORT).show();
        mIvBack.setVisibility(View.INVISIBLE);
        for(Fragment fragment:mFragments){
            BlankFragment listFragment= (BlankFragment) fragment;
            listFragment.tooglePager(true);
        }
    }
}
