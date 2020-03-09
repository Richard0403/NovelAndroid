package com.richard.novel.view.home.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.contract.RecBookContract;
import com.richard.novel.presenter.impl.RecBookContractImpl;
import com.richard.novel.view.base.BaseMvpListFragment;
import com.richard.novel.view.home.activity.BookSearchActivity;
import com.richard.novel.view.home.adapter.BookCategoryAdapter;
import com.richard.novel.view.home.adapter.BookCommonAdapter;
import com.richard.novel.view.home.banner.BannerUtils;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.ListHorizontalTitle;
import com.scrollablelayout.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class RecommendFragment extends BaseMvpListFragment<RecBookContract.Presenter> implements RecBookContract.View {
    @BindView(R.id.cb_banner)
    ConvenientBanner cb_banner;
    @BindView(R.id.clv_hot)
    RecyclerView clv_hot;
    @BindView(R.id.slv_content)
    ScrollableLayout slv_content;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.lht_hot)
    ListHorizontalTitle lht_hot;

    private List<BookInfo> bookInfos = new ArrayList<>();


    public RecommendFragment() {
        // Required empty public constructor
    }


    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }


    @Override
    protected BaseQuickAdapter initAdapter() {
        return new BookCommonAdapter(bookInfos);
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.loadHotBookList(pageNo,pageSize);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_hot;
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.GONE);
    }


    @Override
    protected void initView() {
        slv_content.getHelper().setCurrentScrollableContainer(clv_hot);
        lht_hot.setRightClickListener(v -> ToastUtil.showSingleToast("没有更多，一直往下滑就行^_^"));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        cb_banner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        cb_banner.stopTurning();
    }




    @Override
    public void setHotBooks(List<BookInfo> hotBooks) {
        if(getPageNo() == 0){
            bookInfos.clear();
        }
        if (!hotBooks.isEmpty()) {
            bookInfos.addAll(hotBooks);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(hotBooks);
    }

    @Override
    public void showError() {

    }

    @Override
    protected RecBookContract.Presenter bindPresenter() {
        return new RecBookContractImpl(this);
    }


    @Override
    public void setBanner(List<BannerInfo> bannerList) {
        BannerUtils.setPages(cb_banner, getActivity(), bannerList);
    }

}
