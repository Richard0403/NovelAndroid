package com.richard.novel.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.presenter.BaseContract;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.widget.LoadMoreFooterView;
import com.scrollablelayout.ScrollableHelper;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/28.
 */

public abstract class BaseMvpListFragment<T extends BaseContract.BasePresenter>  extends BaseMVPFragment<T> implements ScrollableHelper.ScrollableContainer{
    protected BaseQuickAdapter baseQuickAdapter;
    private int PAGE_SIZE = 20;
    private int pageNo = 0;

    protected abstract BaseQuickAdapter initAdapter();

    protected abstract void reqList(int pageNo, int pageSize);

    protected abstract RecyclerView getRecyclerView();

    protected abstract void listEmpty();

    @Override
    protected void initData() {
        super.initData();
        baseQuickAdapter = initAdapter();
        getRecyclerView().setAdapter(baseQuickAdapter);
        baseQuickAdapter.setEnableLoadMore(true);
        baseQuickAdapter.setLoadMoreView(new LoadMoreFooterView());
        baseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                reqList(pageNo, PAGE_SIZE);
            }
        }, getRecyclerView());
        reqList(pageNo, PAGE_SIZE);
    }

    protected void setReqListSuccess(List objects) {
        if (pageNo == 0) {
            if (objects == null || objects.isEmpty()) {
                listEmpty();
            }
        }
        baseQuickAdapter.loadMoreComplete();
        baseQuickAdapter.setEnableLoadMore(objects.size() >= PAGE_SIZE);
        pageNo++;
        baseQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public View getScrollableView() {
        return getRecyclerView();
    }

    protected void resetPageNo(){
        pageNo = 0;
    }

    protected int getPageNo(){
        return pageNo;
    }

    protected int getPageSize(){
        return PAGE_SIZE;
    }

    protected void resetPageSize(int pageSize){
        PAGE_SIZE = pageSize;
    }
}
