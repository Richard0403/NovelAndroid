package com.richard.novel.view.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.presenter.impl.HomeContractImpl;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.view.base.BaseFragmentPagerAdapter;
import com.richard.novel.view.base.BaseMVPFragment;
import com.richard.novel.view.base.BaseMvpListFragment;
import com.richard.novel.view.home.activity.BookSearchActivity;
import com.richard.novel.view.home.adapter.BookCategoryAdapter;
import com.richard.novel.view.home.adapter.BookCommonAdapter;
import com.richard.novel.view.home.banner.BannerUtils;
import com.richard.novel.view.home.fragment.BookClassifyFragment;
import com.richard.novel.view.home.fragment.RecommendFragment;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.ListHorizontalTitle;
import com.richard.novel.widget.NoManyScrollViewPager;
import com.richard.novel.widget.SearchWidget;
import com.richard.novel.widget.WidthTabLayout;
import com.scrollablelayout.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class FragmentHome extends BaseMVPFragment<HomeContract.Presenter> implements HomeContract.View {


    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.tab_content)
    WidthTabLayout tab_content;
    @BindView(R.id.vp_content)
    NoManyScrollViewPager vp_content;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    @Override
    protected void initData() {
        super.initData();
        mPresenter.checkUpdate();
        mPresenter.loadBookCategory();
    }

    @Override
    protected void initView() {

    }



    @Override
    protected HomeContract.Presenter bindPresenter() {
        return new HomeContractImpl(this);
    }
    @Override
    public void showError() {

    }
    @OnClick({R.id.ll_search})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.ll_search:
                BookSearchActivity.start(getContext(), BookSearchActivity.class);
                break;
        }
    }

    @Override
    public void setBookCategory(List<BookCategory> bookCategories) {
        //分类
        bookCategories.add(0,new BookCategory(-1, "推荐"));
        String title[] = new String[bookCategories.size()];
        List<Fragment> fragments = new ArrayList<>(bookCategories.size());
        for (int i = 0; i<bookCategories.size(); i++){
            title[i] = bookCategories.get(i).getName();
            tab_content.addTab(title[i]);
            if(i == 0){
                fragments.add(RecommendFragment.newInstance());
            }else{
                fragments.add(BookClassifyFragment.newInstance(bookCategories.get(i).getCodeX()));
            }
        }
        BaseFragmentPagerAdapter pagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), title, fragments);
        vp_content.setAdapter(pagerAdapter);
        vp_content.setOffscreenPageLimit(5);
//        tab_content.addOnTabSelectedListener(null);
        vp_content.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_content.getTabLayout()));
        tab_content.setupWithViewPager(vp_content);
    }
}
