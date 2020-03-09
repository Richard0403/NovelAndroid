package com.richard.novel.view.main.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.richard.novel.R;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.utils.SystemInfo;
import com.richard.novel.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected int getLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        SystemInfo.setImmersiveMode(true, getWindow().getDecorView());
    }

    @Override
    protected void initData() {
        initViewPager();
    }

    private void initViewPager() {
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext(), R.layout.item_guide, null);
                ImageView imageView = view.findViewById(R.id.guide_bg);
                View view1 = view.findViewById(R.id.guide_v_1);
                View view2 = view.findViewById(R.id.guide_v_2);
                View view3 = view.findViewById(R.id.guide_v_3);
                Button button = view.findViewById(R.id.btn_enter);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.mipmap.bg_guide_1);
                        break;
                    case 1:
                        imageView.setImageResource(R.mipmap.bg_guide_2);
                        break;
                    case 2:
                        imageView.setImageResource(R.mipmap.bg_guide_3);
                        break;
                }
                List<View> viewList = new ArrayList<>();
                viewList.add(view1);
                viewList.add(view2);
                viewList.add(view3);
                viewList.get(position).setBackgroundResource(R.drawable.bg_round_theme);
                if (position == 2) {
                    button.setVisibility(View.VISIBLE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(AppCache.getUserInfo() == null){
                                openLogin();
                            }else{
                                openMain();
                            }

                        }
                    });
                }
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }
            @Override
            public int getCount() {
                return 3;
            }
        };
        mViewPager.setAdapter(adapter);
    }

    private void openMain() {
        start(mContext, MainActivity.class);
        finish();
    }


    private void openLogin(){
        start(mContext, SignInActivity.class);
        finish();
    }
}
